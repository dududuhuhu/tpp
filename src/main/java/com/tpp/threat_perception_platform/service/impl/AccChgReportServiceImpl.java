package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.dao.AcctChgLogMapper;
import com.tpp.threat_perception_platform.param.ReportParam;
import com.tpp.threat_perception_platform.pojo.AcctChgLog;
import com.tpp.threat_perception_platform.service.AccChgReportService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccChgReportServiceImpl implements AccChgReportService {

    @Autowired
    private AcctChgLogMapper acctChgLogMapper;

    @Override
    public JSONObject analyzeAndGenerateReportByMac(String mac) {
        // 查找该 MAC 的所有日志（按时间升序）
        List<AcctChgLog> logs = acctChgLogMapper.selectByMacOrderedByTime(mac);

        if (logs == null || logs.isEmpty()) {
            throw new RuntimeException("未找到账号变更日志，MAC: " + mac);
        }

        // 拼接日志信息
        StringBuilder logInfoBuilder = new StringBuilder();
        for (AcctChgLog log : logs) {
            logInfoBuilder.append(String.format(
                    "事件ID：%s\n事件时间：%s\n操作行为：%s\n被操作用户：%s\n操作用户：%s\n\n",
                    log.getEventId(), log.getEventTime(), log.getAction(), log.getTargetUser(), log.getOperatorUser()
            ));
        }

        // AI 提示词模板
        String promptTemplate =
                "作为专业的日志数据风险分析师，我将根据您提供的账号变更日志信息，生成一个包含关键指标的风险分析报告。请仔细阅读以下日志信息，并根据您的专业知识和经验进行风险评估。\n" +
                        "日志信息如下：\n" +
                        "%s\n" +
                        "风险评估指南：\n" +
                        "1. **整体风险等级**：综合考虑操作行为、时间、操作频率等因素，评估整体风险等级为“高”、“中”或“低”。\n" +
                        "   - **高风险**：存在多个可疑操作行为（如权限提升、账号删除等），且操作时间多在非正常工作时间（如凌晨0点至6点）；或者同一用户在短时间内频繁进行高风险操作。\n" +
                        "   - **中风险**：存在可疑操作行为，但操作时间在正常工作时间（如上午9点至下午6点）；或者操作频率较高，但操作行为均为常规操作（如密码变更）。\n" +
                        "   - **低风险**：所有操作均为常规操作，且操作时间均在正常工作时间。\n" +
                        "2. **高风险事件数量**：统计高风险事件的数量，并列出这些事件的详细信息。\n" +
                        "3. **风险评分**：为每个事件分配一个风险评分（0-10，10为最高风险），并以列表形式展示。\n" +
                        "4. **操作频率**：统计每个用户的操作频率，特别是高风险操作的频率，并以列表形式展示。\n" +
                        "5. **风险描述**：详细描述可能导致风险的原因，例如操作是否频繁、时间是否异常、行为是否可疑等。请综合考虑所有日志事件，提供一个总体的风险描述，并特别指出高风险事件。\n" +
                        "6. **建议措施**：根据风险等级，提出相应的应对措施，例如立即审查操作日志、限制用户权限等。\n" +
                        "\n" +
                        "请根据上述信息，生成风险分析报告，报告格式如下：\n" +
                        "{\n" +
                        "  \"overall_risk_level\": \"整体风险等级（高、中、低）\",\n" +
                        "  \"high_risk_event_count\": \"高风险事件数量\",\n" +
                        "  \"high_risk_events\": [\n" +
                        "    {\n" +
                        "      \"event_id\": \"事件ID\",\n" +
                        "      \"risk_level\": \"事件风险等级\",\n" +
                        "      \"risk_score\": \"事件风险评分\",\n" +
                        "      \"risk_description\": \"事件风险描述\",\n" +
                        "      \"event_time\": \"事件时间\",\n" +
                        "      \"action\": \"操作行为\",\n" +
                        "      \"target_user\": \"被操作用户\",\n" +
                        "      \"operator_user\": \"操作用户\"\n" +
                        "    }\n" +
                        "    // 其他高风险事件\n" +
                        "  ],\n" +
                        "  \"risk_scores\": [\n" +
                        "    {\n" +
                        "      \"event_id\": \"事件ID\",\n" +
                        "      \"risk_score\": \"事件风险评分\"\n" +
                        "    }\n" +
                        "    // 其他事件的风险评分\n" +
                        "  ],\n" +
                        "  \"operation_frequencies\": [\n" +
                        "    {\n" +
                        "      \"user\": \"用户\",\n" +
                        "      \"frequency\": \"操作频率\"\n" +
                        "    }\n" +
                        "    // 其他用户的操作频率\n" +
                        "  ],\n" +
                        "  \"risk_description\": \"总体风险描述\",\n" +
                        "  \"suggested_action\": \"建议措施\"\n" +
                        "}\n" +
                        "\n" +
                        "请只返回纯 JSON 数据（不要包含任何代码块标记、markdown符号、分析依据等）。";

        String prompt = String.format(promptTemplate, logInfoBuilder.toString());

        try {
            GenerationResult result = AIUtils.callWithMessage(prompt);
            String content = result.getOutput().getChoices().get(0).getMessage().getContent();

            // 清理多余符号
            content = content.replaceAll("(?i)```json", "")
                    .replaceAll("```", "")
                    .trim();

            // 直接解析为 JSONObject 返回
            return JSON.parseObject(content);

        } catch (Exception e) {
            // 如果 AI 失败，返回默认报告
            JSONObject fallback = new JSONObject();
            fallback.put("overall_risk_level", "低");
            fallback.put("high_risk_event_count", 0);
            fallback.put("high_risk_events", List.of());
            fallback.put("risk_scores", List.of());
            fallback.put("operation_frequencies", List.of());
            fallback.put("risk_description", "AI 分析失败，默认低风险。错误信息: " + e.getMessage());
            fallback.put("suggested_action", "请人工复核该日志。");
            return fallback;
        }
    }
}
