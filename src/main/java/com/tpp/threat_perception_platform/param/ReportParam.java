package com.tpp.threat_perception_platform.param;

import java.util.Date;

public class ReportParam {
    // 账号变更日志相关字段
    private String mac;                 // 主机MAC地址
    private String eventId;             // 事件ID
    private Date eventTime;             // 事件时间
    private String action;              // 操作行为描述
    private String targetUser;          // 被操作用户
    private String operatorUser;        // 操作用户

    // 风险分析报告相关字段
    private String riskLevel;           // 风险等级（低、中、高）
    private String riskDescription;    // 风险描述
    private String suggestedAction;    // 建议措施

    private Date analysisTime;          // 分析时间

    // 省略构造方法、getter、setter，方便代码生成工具自动生成

    public ReportParam() {}

    // getter & setter

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    public String getOperatorUser() {
        return operatorUser;
    }

    public void setOperatorUser(String operatorUser) {
        this.operatorUser = operatorUser;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    public String getSuggestedAction() {
        return suggestedAction;
    }

    public void setSuggestedAction(String suggestedAction) {
        this.suggestedAction = suggestedAction;
    }

    public Date getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }
}
