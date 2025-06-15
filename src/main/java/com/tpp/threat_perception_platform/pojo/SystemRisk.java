package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 
 * @TableName system_risk
 */
public class SystemRisk {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String mac;

    /**
     * 
     */
    private Integer ruleId;

    /**
     * 
     */
    private String riskName;

    /**
     * 
     */
    private String riskType;

    /**
     * 
     */
    private Integer riskLevel;

    /**
     * 
     */
    private Integer isRisky;

    /**
     * 
     */
    private String detectionOutput;

    /**
     * 
     */
    private String riskDetail;

    /**
     * 
     */
    private String remediationAdvice;

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
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
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
     * 
     */
    public Integer getRuleId() {
        return ruleId;
    }

    /**
     * 
     */
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * 
     */
    public String getRiskName() {
        return riskName;
    }

    /**
     * 
     */
    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    /**
     * 
     */
    public String getRiskType() {
        return riskType;
    }

    /**
     * 
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    /**
     * 
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * 
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 
     */
    public Integer getIsRisky() {
        return isRisky;
    }

    /**
     * 
     */
    public void setIsRisky(Integer isRisky) {
        this.isRisky = isRisky;
    }

    /**
     * 
     */
    public String getDetectionOutput() {
        return detectionOutput;
    }

    /**
     * 
     */
    public void setDetectionOutput(String detectionOutput) {
        this.detectionOutput = detectionOutput;
    }

    /**
     * 
     */
    public String getRiskDetail() {
        return riskDetail;
    }

    /**
     * 
     */
    public void setRiskDetail(String riskDetail) {
        this.riskDetail = riskDetail;
    }

    /**
     * 
     */
    public String getRemediationAdvice() {
        return remediationAdvice;
    }

    /**
     * 
     */
    public void setRemediationAdvice(String remediationAdvice) {
        this.remediationAdvice = remediationAdvice;
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
        SystemRisk other = (SystemRisk) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getRuleId() == null ? other.getRuleId() == null : this.getRuleId().equals(other.getRuleId()))
            && (this.getRiskName() == null ? other.getRiskName() == null : this.getRiskName().equals(other.getRiskName()))
            && (this.getRiskType() == null ? other.getRiskType() == null : this.getRiskType().equals(other.getRiskType()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getIsRisky() == null ? other.getIsRisky() == null : this.getIsRisky().equals(other.getIsRisky()))
            && (this.getDetectionOutput() == null ? other.getDetectionOutput() == null : this.getDetectionOutput().equals(other.getDetectionOutput()))
            && (this.getRiskDetail() == null ? other.getRiskDetail() == null : this.getRiskDetail().equals(other.getRiskDetail()))
            && (this.getRemediationAdvice() == null ? other.getRemediationAdvice() == null : this.getRemediationAdvice().equals(other.getRemediationAdvice()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getRuleId() == null) ? 0 : getRuleId().hashCode());
        result = prime * result + ((getRiskName() == null) ? 0 : getRiskName().hashCode());
        result = prime * result + ((getRiskType() == null) ? 0 : getRiskType().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getIsRisky() == null) ? 0 : getIsRisky().hashCode());
        result = prime * result + ((getDetectionOutput() == null) ? 0 : getDetectionOutput().hashCode());
        result = prime * result + ((getRiskDetail() == null) ? 0 : getRiskDetail().hashCode());
        result = prime * result + ((getRemediationAdvice() == null) ? 0 : getRemediationAdvice().hashCode());
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
        sb.append(", ruleId=").append(ruleId);
        sb.append(", riskName=").append(riskName);
        sb.append(", riskType=").append(riskType);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", isRisky=").append(isRisky);
        sb.append(", detectionOutput=").append(detectionOutput);
        sb.append(", riskDetail=").append(riskDetail);
        sb.append(", remediationAdvice=").append(remediationAdvice);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}