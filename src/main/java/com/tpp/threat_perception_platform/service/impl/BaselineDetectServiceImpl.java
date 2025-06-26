package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.BaselineDetectAiReportMapper;
import com.tpp.threat_perception_platform.dao.BaselineDetectMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaselineDetectService;
import com.tpp.threat_perception_platform.service.RabbitService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import com.tpp.threat_perception_platform.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class BaselineDetectServiceImpl implements BaselineDetectService {

    @Autowired
    private BaselineDetectMapper baselineDetectMapper;

    @Autowired
    private BaselineDetectAiReportMapper baselineDetectAiReportMapper;

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private RabbitService rabbitService;

    /**
     * 保存
     */
    @Override
    public ResponseResult saveBaselineDetect(BaselineDetect baselineDetect) {
        // 先查询是否已存在
        BaselineDetect db = baselineDetectMapper.selectByMacAndName(baselineDetect.getMac(), baselineDetect.getName());
        // 添加
        baselineDetect.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        if (db != null) {
            // 若已存在，更新字段（只更新 updated_time 或者所有字段）
            // 方式 1：只更新 updated_time
            // db.setUpdatedTime(now);
            // baselineDetectMapper.updateUpdatedTimeById(db);

            // 方式 2：更新所有字段（推荐）
            baselineDetect.setId(db.getId()); // 设置主键，用于 where 条件
            baselineDetectMapper.updateByPrimaryKey(baselineDetect);

            return new ResponseResult<>(0, "记录已存在，已更新时间戳");
        }
        baselineDetectMapper.insert(baselineDetect);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 查询列表（分页）
     */
    @Override
    public ResponseResult baselineDetectList(BaselineDetectParam param) {
        String mac= param.getMac();
        System.out.println("mac:"+mac);
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<BaselineDetect> baselineDetectList = baselineDetectMapper.findAll();
        // 构架pageInfo
        PageInfo<BaselineDetect> pageInfo = new PageInfo<>(baselineDetectList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult baselineDetectDiscovery() {
        String type="baselineDetect";
        List<Host> db_hostList = hostMapper.findAll();
        if(db_hostList.isEmpty()){
            return new ResponseResult<>(1003,"无主机在线！");
        }
        for(Host host : db_hostList){
            // 去比较更新时间和当前时间判断主机是否在线
            if (host != null && host.getUpdateTime() != null && new Date().getTime() - host.getUpdateTime().getTime() < 4000)
            {
                Map<String, Object> map = new HashMap<>();
                map.put("type", "baselineDetect");
//                List<String> names = accountInfoMapper.selectAllNamesByMac(host.getMacAddress());
//                map.put("username",names);
                String json = JSON.toJSONString(map);  // 结果是 {"type":"auditLog"}
                // 组装队列的名字
                String routingKey=host.getMacAddress().replace(":","");
                rabbitService.sendMessage("agent_exchange",routingKey,json);
            }
        }
        return new ResponseResult(0, "开始同步，请稍后查看！");
    }

    @Override
    public ResponseResult analyzeAndSaveBaselineDetectReport(String mac) {
        try {
            // 1. 根据 mac 查询所有相关的基线探测记录
            List<BaselineDetect> baselineList = baselineDetectMapper.selectByMac(mac);
            if (baselineList == null || baselineList.isEmpty()) {
                return new ResponseResult(-1, "未找到该MAC的基线探测记录");
            }

            // 2. 生成摘要内容（拼接关键字段）
            String riskSummaryContent = buildBaselineSummaryContent(baselineList);

            // 3. 计算摘要哈希
            String riskHash = HashUtils.generateSHA256(riskSummaryContent);

            // 4. 查询已有 AI 分析报告
            BaselineDetectAiReport existing = baselineDetectAiReportMapper.selectByMac(mac);

            // 5. 判断是否需要调用 AI（已有报告且hash一致则跳过）
            boolean needAI = existing == null || !riskHash.equals(existing.getRiskHash());
            if (!needAI) {
                return new ResponseResult(0, "已存在相同分析，无需重新生成", existing);
            }

            // 6. 构建新的报告对象
            BaselineDetectAiReport report = new BaselineDetectAiReport();
            report.setMac(mac);
            report.setRiskHash(riskHash);
            report.setRiskSummaryContent(riskSummaryContent);
            report.setCreatedAt(new Date());
            report.setUpdatedAt(new Date());

            // 7. 构建提示词并调用 AI
            String promptTemplate =
                    "作为专业网络安全分析师，请根据以下基线探测记录生成风险分析报告，返回指定JSON格式。\n\n探测记录：\n%s\n\n返回格式：\n"
                            + "{\"risk_summary\": \"整体风险情况\", \"reason\": \"主要风险原因\", \"consequence\": \"可能后果\", \"suggestion\": \"修复建议\"}\n"
                            + "仅返回上述JSON格式，无其他内容。";

            String risksInfo = buildBaselineInfoForPrompt(baselineList);
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

            // 8. 插入或更新报告
            baselineDetectAiReportMapper.insertOrUpdate(report);

            return new ResponseResult(0, "AI分析成功", report);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseResult(-1, "AI分析过程中发生异常: " + ex.getMessage());
        }
    }

    private String buildBaselineSummaryContent(List<BaselineDetect> list) {
        list.sort(Comparator.comparing(BaselineDetect::getUpdatedTime));
        StringBuilder sb = new StringBuilder();
        for (BaselineDetect detect : list) {
            sb.append(detect.getName())
                    .append(detect.getResult())
                    .append(detect.getCurrentValue())
                    .append(detect.getRecommendedValue())
                    .append(detect.getDescription())
                    .append(detect.getUpdatedTime() != null ? detect.getUpdatedTime().toString() : "");
        }
        return sb.toString();
    }

    private String buildBaselineInfoForPrompt(List<BaselineDetect> list) {
        StringBuilder sb = new StringBuilder();
        for (BaselineDetect detect : list) {
            sb.append(String.format(
                    "检测项: %s\n当前状态: %s\n当前值: %s\n推荐值: %s\n说明: %s\n更新时间: %s\n\n",
                    detect.getName(),
                    detect.getResult(),
                    detect.getCurrentValue(),
                    detect.getRecommendedValue(),
                    detect.getDescription(),
                    detect.getUpdatedTime() != null ? detect.getUpdatedTime().toString() : ""
            ));
        }
        return sb.toString();
    }

    private String cleanAIContent(String content) {
        return content.replaceAll("(?i)```json", "")
                .replaceAll("```", "")
                .trim();
    }


}
