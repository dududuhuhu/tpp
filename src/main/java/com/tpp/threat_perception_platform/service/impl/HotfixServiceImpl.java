package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.dao.HotfixMapper;
import com.tpp.threat_perception_platform.dao.HotfixRiskAiReportMapper;
import com.tpp.threat_perception_platform.dao.WinCveDbMapper;
import com.tpp.threat_perception_platform.param.HotfixParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.pojo.WinCveDb;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HotfixService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import com.tpp.threat_perception_platform.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotfixServiceImpl implements HotfixService {

    @Autowired
    private HotfixMapper hotfixMapper;

    @Autowired
    private WinCveDbMapper winCveDbMapper;

    @Autowired
    private HotfixRiskAiReportMapper hotfixRiskAiReportMapper;

    /**
     * 查询
     */
    /**
     * @Override
    public ResponseResult hotfixList(HotfixParam param) {
        String mac= param.getMacAddress();
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<Hotfix> hotfixList = hotfixMapper.findAll(mac);
        // 构架pageInfo
        PageInfo<Hotfix> pageInfo = new PageInfo<>(hotfixList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
    */

    /**
     * 保存
     */
    @Override
    public ResponseResult saveHotfix(Hotfix hotfix, Timestamp now) {
        // 先查询是否已存在（根据 mac + hotfixId 判断是否重复）
        Hotfix db = hotfixMapper.selectByMacAndHotfixId(hotfix.getMac(), hotfix.getHotfixId());
        if (db != null) {
            // 若已存在，更新字段（只更新 updated_time 或者所有字段）
            hotfix.setId(db.getId()); // 设置主键，用于 where 条件
            hotfixMapper.updateByPrimaryKey(hotfix);
            return new ResponseResult<>(1003, "该补丁记录已存在！");
        }
        // 添加
        hotfix.setUpdatedTime(now);
        hotfixMapper.insertSelective(hotfix);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 更新
     */
//    @Override
//    public ResponseResult editApp(AppInfo appInfo) {
//        appInfoMapper.updateByPrimaryKeySelective(appInfo);
//        return new ResponseResult<>(0, "更新成功！");
//    }

//    @Override
//    public ResponseResult<List<DangerousHotfix>> getDangerousPatches(String mac) {
//        // 默认返回前100条
//        return getDangerousPatch(1, 100);
//    }

    @Override
    public ResponseResult<List<DangerousHotfix>> getDangerousPatch(HotfixParam param) {
        int page = param.getPage() != null ? param.getPage() : 1;
        int limit = param.getLimit() != null ? param.getLimit() : 10;

        PageHelper.startPage(page, limit); // 启动分页
        System.out.println("hotfixparam:"+param);

        // === 查询补丁列表 ===
        List<Hotfix> allHotfixes;
        if (param.getMacAddress() != null && !param.getMacAddress().isEmpty()) {
            allHotfixes = hotfixMapper.findByMac(param.getMacAddress());
        } else {
            allHotfixes = hotfixMapper.findAll();
        }
        System.out.println("allHotfixes:"+allHotfixes);

        List<DangerousHotfix> result = new ArrayList<>();
        for (Hotfix hotfix : allHotfixes) {
            String hotfixId = hotfix.getHotfixId();
            List<WinCveDb> matchedCves = winCveDbMapper.findByHotfixId(hotfixId);
            for (WinCveDb cve : matchedCves) {
                DangerousHotfix vo = new DangerousHotfix();
                vo.setMacAddress(hotfix.getMac());
                vo.setHotfixId(hotfixId);
                vo.setCve(cve.getCve());
                vo.setScore(cve.getScore());
                result.add(vo);
            }
        }

        // PageInfo 包装分页信息（注意：分页的是 result）
        PageInfo<DangerousHotfix> pageInfo = new PageInfo<>(result);

        return new ResponseResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public ResponseResult analyzeAndSaveHotfixRiskReport(List<DangerousHotfix> hotfixes, String mac) {

        try {
            // 1. 过滤出当前 mac 对应的补丁列表
            List<DangerousHotfix> filteredHotfixes = hotfixes.stream()
                    .filter(h -> mac.equalsIgnoreCase(h.getMacAddress()))
                    .collect(Collectors.toList());

            if (filteredHotfixes.isEmpty()) {
                return new ResponseResult(-1, "未找到该MAC对应的危险补丁记录");
            }

            // 2. 构造摘要字符串并生成哈希
            String riskSummaryContent = buildHotfixSummaryContent(filteredHotfixes);
            String riskHash = HashUtils.generateSHA256(riskSummaryContent);

            // 3. 查询已有的报告（假设此处存数据库中）
            HotfixRiskAiReport existing = hotfixRiskAiReportMapper.selectByMac(mac);
            boolean needAI = existing == null || !riskHash.equals(existing.getRiskHash());

            if (!needAI) {
                return new ResponseResult(0, "已存在相同分析，无需重新生成", existing);
            }

            // 4. 构建新报告对象
            HotfixRiskAiReport report = new HotfixRiskAiReport();
            report.setMac(mac);
            report.setRiskHash(riskHash);
            report.setRiskSummaryContent(riskSummaryContent);
            report.setCreatedAt(new Date());
            report.setUpdatedAt(new Date());

            // 5. AI 分析
            if (needAI) {
                String promptTemplate =
                        "作为专业网络安全分析师，请根据以下系统安装补丁的情况，判断是否存在安全风险，并返回指定 JSON 格式。\n\n补丁信息如下：\n%s\n\n返回格式：\n"
                                + "{\"risk_summary\": \"整体风险情况\", \"reason\": \"主要风险原因\", \"consequence\": \"可能后果\", \"suggestion\": \"修复建议\"}\n"
                                + "仅返回上述 JSON 格式，无需其他内容。";

                String hotfixInfo = buildHotfixInfoForPrompt(filteredHotfixes);
                String prompt = String.format(promptTemplate, hotfixInfo);

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

            // 6. 保存分析报告（插入或更新）
            hotfixRiskAiReportMapper.insertOrUpdate(report);

            // 7. 返回分析结果
            return new ResponseResult(0, "AI分析成功", report);

        } catch (Exception ex) {
            // 捕获所有异常，返回失败结果
            ex.printStackTrace();
            return new ResponseResult(-1, "AI分析过程中发生异常: " + ex.getMessage());
        }
    }

    private String buildHotfixInfoForPrompt(List<DangerousHotfix> hotfixes) {
        StringBuilder sb = new StringBuilder();
        for (DangerousHotfix hotfix : hotfixes) {
            sb.append(String.format(
                    "补丁编号: %s\n主机MAC: %s\n关联CVE: %s\n漏洞评分: %s\n\n",
                    hotfix.getHotfixId(),
                    hotfix.getMacAddress(),
                    hotfix.getCve(),
                    hotfix.getScore()
            ));
        }
        return sb.toString();
    }

    private String buildHotfixSummaryContent(List<DangerousHotfix> hotfixes) {
        hotfixes.sort(Comparator.comparing(DangerousHotfix::getHotfixId));
        StringBuilder sb = new StringBuilder();
        for (DangerousHotfix hotfix : hotfixes) {
            sb.append(hotfix.getHotfixId())
                    .append(hotfix.getCve())
                    .append(hotfix.getScore());
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
