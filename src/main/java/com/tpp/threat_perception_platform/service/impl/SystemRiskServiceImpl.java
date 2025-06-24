package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.SystemRiskAiReportMapper;
import com.tpp.threat_perception_platform.dao.SystemRiskMapper;
import com.tpp.threat_perception_platform.dao.SystemRiskRulesMapper;
import com.tpp.threat_perception_platform.param.SystemRiskParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRisk;
import com.tpp.threat_perception_platform.pojo.SystemRisk;
import com.tpp.threat_perception_platform.pojo.SystemRiskAiReport;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.SystemRiskService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import com.tpp.threat_perception_platform.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class SystemRiskServiceImpl implements SystemRiskService {

    @Autowired
    private SystemRiskMapper systemRiskMapper;

    @Autowired
    private SystemRiskRulesMapper systemRiskRuleMapper;

    @Autowired
    private SystemRiskAiReportMapper systemRiskAiReportMapper;

    @Override
    public ResponseResult systemRiskList(SystemRiskParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<SystemRisk> list = systemRiskMapper.selectByParam(param);
        // 构架pageInfo
        PageInfo<SystemRisk> pageInfo = new PageInfo<>(list);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult saveSystemRisk(SystemRisk systemRisk) {
        try {
            // 设置创建时间，如果为空
            if (systemRisk.getCreatedAt() == null) {
                systemRisk.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            }

            // 更新时间直接设当前时间
            systemRisk.setUpdatedAt(new Date());

            int insertResult = systemRiskMapper.insertSelective(systemRisk);
            System.out.println("插入结果: " + insertResult);
            return new ResponseResult<>(0, "插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(-1, "保存失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseResult editSystemRisk(SystemRisk systemRisk) {
        try {
            // 编辑时更新更新时间
            systemRisk.setUpdatedAt(new Date());
            int updateResult = systemRiskMapper.updateByPrimaryKeySelective(systemRisk);
            System.out.println("更新结果: " + updateResult);
            return new ResponseResult<>(0, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(-1, "编辑失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseResult getSystemRiskDetail(Integer id) {
        SystemRisk risk = systemRiskMapper.selectByPrimaryKey(Long.valueOf(id));
        if (risk == null) {
            return new ResponseResult<>(-1, "风险记录不存在");
        }
        return new ResponseResult<>(0, risk);
    }

    @Override
    public ResponseResult getRiskCount(SystemRiskParam param) {
        // 统计数据
        int totalScans = systemRiskMapper.countTotalScans(param);
        int foundRisks = systemRiskMapper.countFoundRisks(param);
        int scanErrors = systemRiskMapper.countScanErrors(param);

        double successRate = totalScans == 0 ? 0 : (totalScans - scanErrors) * 100.0 / totalScans;

        Map<Integer, Integer> riskLevelDist = systemRiskMapper.groupCountByRiskLevel(param);
        Map<String, Integer> riskTypeDist = systemRiskMapper.groupCountByRiskType(param);

        Map<String, Object> report = new HashMap<>();
        report.put("totalScans", totalScans);
        report.put("foundRisks", foundRisks);
        report.put("scanErrors", scanErrors);
        report.put("successRate", String.format("%.1f%%", successRate));
        report.put("riskLevelDist", riskLevelDist);
        report.put("riskTypeDist", riskTypeDist);

        return new ResponseResult<>(0, "统计成功", report);
    }

    @Override
    public ResponseResult analyzeAndSaveSystemRiskReport(String mac) {
        try {
            // 1. 根据 mac 查询所有相关的系统风险记录
            List<SystemRisk> risks = systemRiskMapper.selectByMac(mac);
            if (risks == null || risks.isEmpty()) {
                // 没有风险记录时，直接返回失败结果
                return new ResponseResult(-1, "未找到该MAC的系统风险记录");
            }

            // 2. 生成风险摘要字符串（将所有风险字段拼接成一段文本）
            String riskSummaryContent = buildSystemRiskSummaryContent(risks);

            // 3. 使用 SHA-256 对摘要字符串生成哈希，用于后续判断是否有变化
            String riskHash = HashUtils.generateSHA256(riskSummaryContent);

            // 4. 查询已有的 AI 分析报告
            SystemRiskAiReport existing = systemRiskAiReportMapper.selectByMac(mac);

            // 5. 判断是否需要重新调用 AI 分析
            boolean needAI = existing == null || !riskHash.equals(existing.getRiskHash());
            if (!needAI) {
                return new ResponseResult(0, "已存在相同分析，无需重新生成", existing);
            }

            // 6. 构造新的报告对象，填充基本信息
            SystemRiskAiReport report = new SystemRiskAiReport();
            report.setMac(mac);
            report.setRiskHash(riskHash);
            report.setRiskSummaryContent(riskSummaryContent);
            report.setCreatedAt(new Date());
            report.setUpdatedAt(new Date());

            // 7. 如果需要调用 AI，准备提示词并调用
            if (needAI) {
                String promptTemplate =
                        "作为专业网络安全分析师，根据以下系统风险记录，请分析风险并返回指定JSON格式。\n\n风险记录：\n%s\n\n返回格式：\n"
                                + "{\"risk_summary\": \"整体风险情况\", \"reason\": \"主要风险原因\", \"consequence\": \"可能后果\", \"suggestion\": \"修复建议\"}\n"
                                + "仅返回上述JSON格式，无其他内容。";

                String risksInfo = buildSystemRisksInfoForPrompt(risks);
                String prompt = String.format(promptTemplate, risksInfo);

                try {
                    GenerationResult result = AIUtils.callWithMessage(prompt);
                    String content = cleanAIContent(result.getOutput().getChoices().get(0).getMessage().getContent());
                    JSONObject json = JSON.parseObject(content);

                    report.setReportContent(content);
                    report.setRiskSummary(json.getString("risk_summary"));
                    report.setReason(json.getString("reason"));
                    report.setConsequence(json.getString("consequence"));
                    report.setSuggestion(json.getString("suggestion"));
                } catch (Exception e) {
                    report.setReportContent("AI分析失败: " + e.getMessage());
                    report.setRiskSummary("AI分析失败");
                }
            }

            // 8. 将报告插入或更新数据库
            systemRiskAiReportMapper.insertOrUpdate(report);

            // 9. 返回成功结果和报告
            return new ResponseResult(0, "AI分析成功", report);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseResult(-1, "AI分析过程中发生异常: " + ex.getMessage());
        }
    }

    /**
     * 拼接系统风险记录信息供AI提示词使用
     */

    private String buildSystemRisksInfoForPrompt(List<SystemRisk> risks) {
        StringBuilder sb = new StringBuilder();
        for (SystemRisk risk : risks) {
            sb.append(String.format(
                    "风险名称: %s\n" +
                            "风险类型: %s\n" +
                            "风险等级: %d\n" +
                            "探测输出: %s\n" +
                            "风险详情: %s\n" +
                            "修复建议: %s\n" +
                            "探测时间: %s\n\n",
                    risk.getRiskName(),
                    risk.getRiskType(),
                    risk.getRiskLevel(),
                    risk.getDetectionOutput(),
                    risk.getRiskDetail(),
                    risk.getRemediationAdvice(),   // 改成 remediationAdvice
                    risk.getCreatedAt() != null ? risk.getCreatedAt().toString() : ""
            ));
        }
        return sb.toString();
    }


    /**
     * 拼接摘要字符串，排序按探测时间
     */
    private String buildSystemRiskSummaryContent(List<SystemRisk> risks) {
        risks.sort(Comparator.comparing(SystemRisk::getCreatedAt));
        StringBuilder sb = new StringBuilder();
        for (SystemRisk risk : risks) {
            sb.append(risk.getRiskName())
                    .append(risk.getRiskType())
                    .append(risk.getRiskLevel())
                    .append(risk.getDetectionOutput())
                    .append(risk.getRiskDetail())
                    .append(risk.getRemediationAdvice())
                    .append(risk.getCreatedAt() != null ? risk.getCreatedAt().toString() : "");
        }
        return sb.toString();
    }

    /**
     * 清理AI返回内容，去除多余格式
     */
    private String cleanAIContent(String content) {
        return content.replaceAll("(?i)```json", "")
                .replaceAll("```", "")
                .trim();
    }


}
