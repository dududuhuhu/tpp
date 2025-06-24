package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 
 * @TableName system_risk_ai_report
 */
public class SystemRiskAiReport {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String mac;

    /**
     * 风险记录摘要哈希
     */
    private String riskHash;

    /**
     * 完整AI报告文本
     */
    private String reportContent;

    /**
     * 整体风险结论，如“存在高风险”
     */
    private String riskSummary;

    /**
     * 主要风险原因
     */
    private String reason;

    /**
     * 可能后果
     */
    private String consequence;

    /**
     * 修复建议
     */
    private String suggestion;

    /**
     * 参与生成hash的拼接内容，用于审计
     */
    private String riskSummaryContent;

    /**
     * 
     */
    private Date createdAt;

    /**
     * 
     */
    private Date updatedAt;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getMac() {
        return mac;
    }

    /**
     * 
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 风险记录摘要哈希
     */
    public String getRiskHash() {
        return riskHash;
    }

    /**
     * 风险记录摘要哈希
     */
    public void setRiskHash(String riskHash) {
        this.riskHash = riskHash;
    }

    /**
     * 完整AI报告文本
     */
    public String getReportContent() {
        return reportContent;
    }

    /**
     * 完整AI报告文本
     */
    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    /**
     * 整体风险结论，如“存在高风险”
     */
    public String getRiskSummary() {
        return riskSummary;
    }

    /**
     * 整体风险结论，如“存在高风险”
     */
    public void setRiskSummary(String riskSummary) {
        this.riskSummary = riskSummary;
    }

    /**
     * 主要风险原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 主要风险原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 可能后果
     */
    public String getConsequence() {
        return consequence;
    }

    /**
     * 可能后果
     */
    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    /**
     * 修复建议
     */
    public String getSuggestion() {
        return suggestion;
    }

    /**
     * 修复建议
     */
    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    /**
     * 参与生成hash的拼接内容，用于审计
     */
    public String getRiskSummaryContent() {
        return riskSummaryContent;
    }

    /**
     * 参与生成hash的拼接内容，用于审计
     */
    public void setRiskSummaryContent(String riskSummaryContent) {
        this.riskSummaryContent = riskSummaryContent;
    }

    /**
     * 
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
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
        SystemRiskAiReport other = (SystemRiskAiReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getRiskHash() == null ? other.getRiskHash() == null : this.getRiskHash().equals(other.getRiskHash()))
            && (this.getReportContent() == null ? other.getReportContent() == null : this.getReportContent().equals(other.getReportContent()))
            && (this.getRiskSummary() == null ? other.getRiskSummary() == null : this.getRiskSummary().equals(other.getRiskSummary()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getConsequence() == null ? other.getConsequence() == null : this.getConsequence().equals(other.getConsequence()))
            && (this.getSuggestion() == null ? other.getSuggestion() == null : this.getSuggestion().equals(other.getSuggestion()))
            && (this.getRiskSummaryContent() == null ? other.getRiskSummaryContent() == null : this.getRiskSummaryContent().equals(other.getRiskSummaryContent()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getRiskHash() == null) ? 0 : getRiskHash().hashCode());
        result = prime * result + ((getReportContent() == null) ? 0 : getReportContent().hashCode());
        result = prime * result + ((getRiskSummary() == null) ? 0 : getRiskSummary().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getConsequence() == null) ? 0 : getConsequence().hashCode());
        result = prime * result + ((getSuggestion() == null) ? 0 : getSuggestion().hashCode());
        result = prime * result + ((getRiskSummaryContent() == null) ? 0 : getRiskSummaryContent().hashCode());
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
        sb.append(", mac=").append(mac);
        sb.append(", riskHash=").append(riskHash);
        sb.append(", reportContent=").append(reportContent);
        sb.append(", riskSummary=").append(riskSummary);
        sb.append(", reason=").append(reason);
        sb.append(", consequence=").append(consequence);
        sb.append(", suggestion=").append(suggestion);
        sb.append(", riskSummaryContent=").append(riskSummaryContent);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}