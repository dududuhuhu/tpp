package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.ApplicationRiskMapper;
import com.tpp.threat_perception_platform.dao.ApplicationRiskAiReportMapper;
import com.tpp.threat_perception_platform.dao.ApplicationRiskRulesMapper;
import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRisk;
import com.tpp.threat_perception_platform.pojo.ApplicationRiskAiReport;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ApplicationRiskService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import com.tpp.threat_perception_platform.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.util.*;

@Service
public class ApplicationRiskServiceImpl implements ApplicationRiskService {

    @Autowired
    private ApplicationRiskMapper applicationRiskMapper;

    @Autowired
    private ApplicationRiskRulesMapper applicationRiskRuleMapper;


    @Autowired
    private ApplicationRiskAiReportMapper applicationRiskAIReportMapper;

    @Override
    public ResponseResult appRiskList(ApplicationRiskParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<ApplicationRisk> list = applicationRiskMapper.selectByParam(param);
        // 构架pageInfo
        PageInfo<ApplicationRisk> pageInfo = new PageInfo<>(list);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult saveAppRisk(ApplicationRisk appRisk) {
        try {
            appRisk.setDetectionTime(appRisk.getDetectionTime() != null ? appRisk.getDetectionTime() : new Date());

            int insertResult = applicationRiskMapper.insertSelective(appRisk);
            System.out.println("插入结果: " + insertResult);
            return new ResponseResult<>(0, "插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(-1, "保存失败: " + e.getMessage());
        }
    }



    @Override
    public ResponseResult editAppRisk(ApplicationRisk appRisk) {
        applicationRiskMapper.updateByPrimaryKeySelective(appRisk);
        return new ResponseResult<>(0, "编辑成功");
    }

    @Override
    public ResponseResult getAppRiskDetail(Integer id) {
        ApplicationRisk risk = applicationRiskMapper.selectByPrimaryKey(Long.valueOf(id));
        if (risk == null) {
            return new ResponseResult<>(-1, "风险记录不存在");
        }
        return new ResponseResult<>(0, risk);
    }


    @Override
    public ResponseResult getRiskCount(ApplicationRiskParam param) {
        // 统计总扫描次数，发现风险数，错误数，成功率等
        // 统计风险等级分布，风险类型分布

        // 假设数据库有字段：
        // scan_time, risk_level(int), risk_type(string), scan_status (0=成功,1=错误)

        int totalScans = applicationRiskMapper.countTotalScans(param);
        int foundRisks = applicationRiskMapper.countFoundRisks(param);
        int scanErrors = applicationRiskMapper.countScanErrors(param);

        double successRate = totalScans == 0 ? 0 : (totalScans - scanErrors) * 100.0 / totalScans;

        // 风险等级分布，返回Map<Integer, Integer>，key等级，value数量
        Map<Integer, Integer> riskLevelDist = applicationRiskMapper.groupCountByRiskLevel(param);

        // 风险类型分布，Map<String, Integer>
        Map<String, Integer> riskTypeDist = applicationRiskMapper.groupCountByRiskType(param);

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
    public Integer countApplicationRisks() {
        return applicationRiskMapper.countApp();
    }

    @Override
    public ResponseResult analyzeAndSaveAppRiskReport(String mac) {
        try {
            // 1. 根据 mac 查询所有相关的应用风险记录
            List<ApplicationRisk> risks = applicationRiskMapper.selectByMac(mac);
            if (risks == null || risks.isEmpty()) {
                // 没有风险记录时，直接返回失败结果
                return new ResponseResult(-1, "未找到该MAC的应用风险记录");
            }

            // 2. 生成风险摘要字符串（将所有风险字段拼接成一段文本）
            String riskSummaryContent = buildRiskSummaryContent(risks);

            // 3. 使用 SHA-256 对摘要字符串生成哈希，用于后续判断是否有变化
            String riskHash = HashUtils.generateSHA256(riskSummaryContent);

            // 4. 查询已有的 AI 分析报告
            ApplicationRiskAiReport existing = applicationRiskAIReportMapper.selectByMac(mac);

            // 5. 判断是否需要重新调用 AI 分析
            //    - 如果已有报告且风险摘要哈希未变，则不重新调用 AI，直接返回已有报告
            boolean needAI = existing == null || !riskHash.equals(existing.getRiskHash());
            if (!needAI) {
                return new ResponseResult(0, "已存在相同分析，无需重新生成", existing);
            }

            // 6. 构造新的报告对象，填充基本信息
            ApplicationRiskAiReport report = new ApplicationRiskAiReport();
            report.setMac(mac);
            report.setRiskHash(riskHash);
            report.setRiskSummaryContent(riskSummaryContent);
            report.setCreatedAt(new Date());
            report.setUpdatedAt(new Date());

            // 7. 如果需要调用 AI，准备提示词并调用
            if (needAI) {
                // 7.1 根据模板拼接提示词，风险记录信息单独格式化拼接
                String promptTemplate =
                        "作为专业网络安全分析师，根据以下风险记录，请分析风险并返回指定JSON格式。\n\n风险记录：\n%s\n\n返回格式：\n"
                                + "{\"risk_summary\": \"整体风险情况\", \"reason\": \"主要风险原因\", \"consequence\": \"可能后果\", \"suggestion\": \"修复建议\"}\n"
                                + "仅返回上述JSON格式，无其他内容。";

                String risksInfo = buildRisksInfoForPrompt(risks);
                String prompt = String.format(promptTemplate, risksInfo);

                try {
                    // 7.2 调用 AI 接口
                    GenerationResult result = AIUtils.callWithMessage(prompt);

                    // 7.3 清理返回内容（去除 markdown 等格式）
                    String content = cleanAIContent(result.getOutput().getChoices().get(0).getMessage().getContent());

                    // 7.4 解析 JSON 并设置报告字段
                    JSONObject json = JSON.parseObject(content);
                    report.setReportContent(content);
                    report.setRiskSummary(json.getString("risk_summary"));
                    report.setReason(json.getString("reason"));
                    report.setConsequence(json.getString("consequence"));
                    report.setSuggestion(json.getString("suggestion"));
                } catch (Exception e) {
                    // AI调用失败时记录错误信息
                    report.setReportContent("AI分析失败: " + e.getMessage());
                    report.setRiskSummary("AI分析失败");
                }
            }

            // 8. 将报告插入或更新数据库
            applicationRiskAIReportMapper.insertOrUpdate(report);

            // 9. 返回成功结果和报告
            return new ResponseResult(0, "AI分析成功", report);

        } catch (Exception ex) {
            // 捕获所有异常，返回失败结果
            ex.printStackTrace();
            return new ResponseResult(-1, "AI分析过程中发生异常: " + ex.getMessage());
        }
    }

    /**
     * 把多条风险记录拼接成供 AI 提示词使用的字符串
     */
    private String buildRisksInfoForPrompt(List<ApplicationRisk> risks) {
        StringBuilder sb = new StringBuilder();
        for (ApplicationRisk risk : risks) {
            sb.append(String.format(
                    "风险名称: %s\n风险类型: %s\n风险等级: %s\n目标主机: %s\n目标URL: %s\n探测时间: %s\n风险详情: %s\n\n",
                    risk.getRiskName(),
                    risk.getRiskType(),
                    risk.getRiskLevel(),
                    risk.getTargetHost(),
                    risk.getTargetUrl(),
                    risk.getDetectionTime(),
                    risk.getRiskDetail()
            ));
        }
        return sb.toString();
    }

    /**
     * 将风险列表所有字段拼成字符串用于摘要计算哈希
     */
    private String buildRiskSummaryContent(List<ApplicationRisk> risks) {
        risks.sort(Comparator.comparing(ApplicationRisk::getDetectionTime));
        StringBuilder sb = new StringBuilder();
        for (ApplicationRisk risk : risks) {
            sb.append(risk.getRiskName())
                    .append(risk.getRiskType())
                    .append(risk.getRiskLevel())
                    .append(risk.getDetectionTime() != null ? risk.getDetectionTime().toString() : "")
                    .append(risk.getRiskDetail());
        }
        return sb.toString();
    }

    /**
     * 清理AI返回的内容，去掉代码块格式等多余符号
     */
    private String cleanAIContent(String content) {
        return content.replaceAll("(?i)```json", "")
                .replaceAll("```", "")
                .trim();
    }



}
