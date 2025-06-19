package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 账号变更日志风险分析报告表，存储对每条日志事件的风险评估结果
 * @TableName acct_chg_report
 */
public class AcctChgReport {
    /**
     * 主键ID，自增唯一标识每条风险分析报告
     */
    private Long id;

    /**
     * 主机MAC地址，关联acct_chg_log中的mac
     */
    private String mac;

    /**
     * 事件ID，关联acct_chg_log中的event_id
     */
    private String eventId;

    /**
     * 风险等级，如低、中、高
     */
    private String riskLevel;

    /**
     * 风险描述，详细说明该事件的风险点
     */
    private String riskDescription;

    /**
     * 建议措施，针对风险给出的处理建议
     */
    private String suggestedAction;

    /**
     * 风险分析时间，记录报告生成时间
     */
    private Date analysisTime;

    /**
     * 记录创建时间，自动填写当前时间
     */
    private Date createdAt;

    /**
     * 主键ID，自增唯一标识每条风险分析报告
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID，自增唯一标识每条风险分析报告
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 主机MAC地址，关联acct_chg_log中的mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * 主机MAC地址，关联acct_chg_log中的mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 事件ID，关联acct_chg_log中的event_id
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * 事件ID，关联acct_chg_log中的event_id
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * 风险等级，如低、中、高
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * 风险等级，如低、中、高
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 风险描述，详细说明该事件的风险点
     */
    public String getRiskDescription() {
        return riskDescription;
    }

    /**
     * 风险描述，详细说明该事件的风险点
     */
    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    /**
     * 建议措施，针对风险给出的处理建议
     */
    public String getSuggestedAction() {
        return suggestedAction;
    }

    /**
     * 建议措施，针对风险给出的处理建议
     */
    public void setSuggestedAction(String suggestedAction) {
        this.suggestedAction = suggestedAction;
    }

    /**
     * 风险分析时间，记录报告生成时间
     */
    public Date getAnalysisTime() {
        return analysisTime;
    }

    /**
     * 风险分析时间，记录报告生成时间
     */
    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }

    /**
     * 记录创建时间，自动填写当前时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 记录创建时间，自动填写当前时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
        AcctChgReport other = (AcctChgReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getEventId() == null ? other.getEventId() == null : this.getEventId().equals(other.getEventId()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getRiskDescription() == null ? other.getRiskDescription() == null : this.getRiskDescription().equals(other.getRiskDescription()))
            && (this.getSuggestedAction() == null ? other.getSuggestedAction() == null : this.getSuggestedAction().equals(other.getSuggestedAction()))
            && (this.getAnalysisTime() == null ? other.getAnalysisTime() == null : this.getAnalysisTime().equals(other.getAnalysisTime()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getEventId() == null) ? 0 : getEventId().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getRiskDescription() == null) ? 0 : getRiskDescription().hashCode());
        result = prime * result + ((getSuggestedAction() == null) ? 0 : getSuggestedAction().hashCode());
        result = prime * result + ((getAnalysisTime() == null) ? 0 : getAnalysisTime().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
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
        sb.append(", eventId=").append(eventId);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", riskDescription=").append(riskDescription);
        sb.append(", suggestedAction=").append(suggestedAction);
        sb.append(", analysisTime=").append(analysisTime);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}