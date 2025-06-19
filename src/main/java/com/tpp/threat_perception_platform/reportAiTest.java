package com.tpp.threat_perception_platform;

import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.service.AccChgReportService;
import com.tpp.threat_perception_platform.dao.AcctChgLogMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class reportAiTest {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用上下文
        ApplicationContext context = SpringApplication.run(ThreatPerceptionPlatformApplication.class, args);

        // 获取服务和 Mapper
        AccChgReportService service = context.getBean(AccChgReportService.class);
        AcctChgLogMapper logMapper = context.getBean(AcctChgLogMapper.class);

        try {
            // 示例：分析指定 MAC 的所有日志
            String targetMac = "DA:80:83:60:75:2F"; // 替换为实际 MAC

            // 检查是否存在该 MAC 的日志
            var logs = logMapper.selectByMac(targetMac);
            if (logs == null || logs.isEmpty()) {
                System.out.println("没有找到指定 MAC 的日志记录: " + targetMac);
            } else {
                System.out.println("开始分析日志...");
                JSONObject report = service.analyzeAndGenerateReportByMac(targetMac);
                printReport(report);
            }

        } catch (Exception e) {
            System.err.println("发生错误：" + e.getMessage());
            e.printStackTrace();
        } finally {
            SpringApplication.exit(context);
        }
    }

    private static void printReport(JSONObject report) {
        System.out.println("分析结果：");
        System.out.println("整体风险等级: " + report.getString("overall_risk_level"));
        System.out.println("高风险事件数量: " + report.getInteger("high_risk_event_count"));
        System.out.println("高风险事件: " + report.getJSONArray("high_risk_events"));
        System.out.println("风险评分: " + report.getJSONArray("risk_scores"));
        System.out.println("操作频率: " + report.getJSONArray("operation_frequencies"));
        System.out.println("风险描述: " + report.getString("risk_description"));
        System.out.println("建议措施: " + report.getString("suggested_action"));
    }
}
