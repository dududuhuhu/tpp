package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 账号变更日志风险分析报告表
 * @TableName acct_chg_log_report
 */
public class AcctChgLogReport {
    /**
     * 主键
     */
    private Long id;

    /**
     * 设备 MAC 地址
     */
    private String macAddress;

    /**
     * 报告内容摘要哈希（SHA-256）
     */
    private String riskHash;

    /**
     * 整体风险等级（高、中、低）
     */
    private String overallRiskLevel;

    /**
     * 高风险事件数量
     */
    private Integer highRiskEventCount;

    /**
     * 高风险事件 JSON 字符串（数组）
     */
    private String highRiskEvents;

    /**
     * 风险评分 JSON 字符串（数组）
     */
    private String riskScores;

    /**
     * 用户操作频率 JSON 字符串（数组）
     */
    private String operationFrequencies;

    /**
     * 总体风险描述
     */
    private String riskDescription;

    /**
     * 建议措施
     */
    private String suggestedAction;

    /**
     * AI 返回的完整 JSON 报告文本
     */
    private String reportContent;

    /**
     * 记录创建时间
     */
    private Date createdAt;

    /**
     * 记录更新时间
     */
    private Date updatedAt;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 设备 MAC 地址
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * 设备 MAC 地址
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * 报告内容摘要哈希（SHA-256）
     */
    public String getRiskHash() {
        return riskHash;
    }

    /**
     * 报告内容摘要哈希（SHA-256）
     */
    public void setRiskHash(String riskHash) {
        this.riskHash = riskHash;
    }

    /**
     * 整体风险等级（高、中、低）
     */
    public String getOverallRiskLevel() {
        return overallRiskLevel;
    }

    /**
     * 整体风险等级（高、中、低）
     */
    public void setOverallRiskLevel(String overallRiskLevel) {
        this.overallRiskLevel = overallRiskLevel;
    }

    /**
     * 高风险事件数量
     */
    public Integer getHighRiskEventCount() {
        return highRiskEventCount;
    }

    /**
     * 高风险事件数量
     */
    public void setHighRiskEventCount(Integer highRiskEventCount) {
        this.highRiskEventCount = highRiskEventCount;
    }

    /**
     * 高风险事件 JSON 字符串（数组）
     */
    public String getHighRiskEvents() {
        return highRiskEvents;
    }

    /**
     * 高风险事件 JSON 字符串（数组）
     */
    public void setHighRiskEvents(String highRiskEvents) {
        this.highRiskEvents = highRiskEvents;
    }

    /**
     * 风险评分 JSON 字符串（数组）
     */
    public String getRiskScores() {
        return riskScores;
    }

    /**
     * 风险评分 JSON 字符串（数组）
     */
    public void setRiskScores(String riskScores) {
        this.riskScores = riskScores;
    }

    /**
     * 用户操作频率 JSON 字符串（数组）
     */
    public String getOperationFrequencies() {
        return operationFrequencies;
    }

    /**
     * 用户操作频率 JSON 字符串（数组）
     */
    public void setOperationFrequencies(String operationFrequencies) {
        this.operationFrequencies = operationFrequencies;
    }

    /**
     * 总体风险描述
     */
    public String getRiskDescription() {
        return riskDescription;
    }

    /**
     * 总体风险描述
     */
    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    /**
     * 建议措施
     */
    public String getSuggestedAction() {
        return suggestedAction;
    }

    /**
     * 建议措施
     */
    public void setSuggestedAction(String suggestedAction) {
        this.suggestedAction = suggestedAction;
    }

    /**
     * AI 返回的完整 JSON 报告文本
     */
    public String getReportContent() {
        return reportContent;
    }

    /**
     * AI 返回的完整 JSON 报告文本
     */
    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    /**
     * 记录创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 记录创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 记录更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 记录更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AcctChgLogReport other = (AcctChgLogReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMacAddress() == null ? other.getMacAddress() == null : this.getMacAddress().equals(other.getMacAddress()))
            && (this.getRiskHash() == null ? other.getRiskHash() == null : this.getRiskHash().equals(other.getRiskHash()))
            && (this.getOverallRiskLevel() == null ? other.getOverallRiskLevel() == null : this.getOverallRiskLevel().equals(other.getOverallRiskLevel()))
            && (this.getHighRiskEventCount() == null ? other.getHighRiskEventCount() == null : this.getHighRiskEventCount().equals(other.getHighRiskEventCount()))
            && (this.getHighRiskEvents() == null ? other.getHighRiskEvents() == null : this.getHighRiskEvents().equals(other.getHighRiskEvents()))
            && (this.getRiskScores() == null ? other.getRiskScores() == null : this.getRiskScores().equals(other.getRiskScores()))
            && (this.getOperationFrequencies() == null ? other.getOperationFrequencies() == null : this.getOperationFrequencies().equals(other.getOperationFrequencies()))
            && (this.getRiskDescription() == null ? other.getRiskDescription() == null : this.getRiskDescription().equals(other.getRiskDescription()))
            && (this.getSuggestedAction() == null ? other.getSuggestedAction() == null : this.getSuggestedAction().equals(other.getSuggestedAction()))
            && (this.getReportContent() == null ? other.getReportContent() == null : this.getReportContent().equals(other.getReportContent()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMacAddress() == null) ? 0 : getMacAddress().hashCode());
        result = prime * result + ((getRiskHash() == null) ? 0 : getRiskHash().hashCode());
        result = prime * result + ((getOverallRiskLevel() == null) ? 0 : getOverallRiskLevel().hashCode());
        result = prime * result + ((getHighRiskEventCount() == null) ? 0 : getHighRiskEventCount().hashCode());
        result = prime * result + ((getHighRiskEvents() == null) ? 0 : getHighRiskEvents().hashCode());
        result = prime * result + ((getRiskScores() == null) ? 0 : getRiskScores().hashCode());
        result = prime * result + ((getOperationFrequencies() == null) ? 0 : getOperationFrequencies().hashCode());
        result = prime * result + ((getRiskDescription() == null) ? 0 : getRiskDescription().hashCode());
        result = prime * result + ((getSuggestedAction() == null) ? 0 : getSuggestedAction().hashCode());
        result = prime * result + ((getReportContent() == null) ? 0 : getReportContent().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", macAddress=").append(macAddress);
        sb.append(", riskHash=").append(riskHash);
        sb.append(", overallRiskLevel=").append(overallRiskLevel);
        sb.append(", highRiskEventCount=").append(highRiskEventCount);
        sb.append(", highRiskEvents=").append(highRiskEvents);
        sb.append(", riskScores=").append(riskScores);
        sb.append(", operationFrequencies=").append(operationFrequencies);
        sb.append(", riskDescription=").append(riskDescription);
        sb.append(", suggestedAction=").append(suggestedAction);
        sb.append(", reportContent=").append(reportContent);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}