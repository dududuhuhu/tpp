package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 应用风险检测结果表
 * @TableName application_risk
 */
public class ApplicationRisk {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 规则ID
     */
    private Integer ruleId;

    /**
     * 设备MAC地址
     */
    private String mac;

    /**
     * 风险名称
     */
    private String riskName;

    /**
     * 风险类型
     */
    private String riskType;

    /**
     * 风险等级
     */
    private Integer riskLevel;

    /**
     * 目标主机
     */
    private String targetHost;

    /**
     * 目标URL
     */
    private String targetUrl;

    /**
     * 检测时间
     */
    private Date detectionTime;

    /**
     * 是否存在风险：0-否，1-是
     */
    private Integer isRisky;

    /**
     * 风险详情
     */
    private String riskDetail;

    /**
     * 响应内容（截取前1000字符）
     */
    private String responseContent;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 修复建议
     */
    private String remediationAdvice;

    /**
     * 处理状态
     */
    private Object status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 规则ID
     */
    public Integer getRuleId() {
        return ruleId;
    }

    /**
     * 规则ID
     */
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * 设备MAC地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * 设备MAC地址
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 风险名称
     */
    public String getRiskName() {
        return riskName;
    }

    /**
     * 风险名称
     */
    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    /**
     * 风险类型
     */
    public String getRiskType() {
        return riskType;
    }

    /**
     * 风险类型
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    /**
     * 风险等级
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * 风险等级
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 目标主机
     */
    public String getTargetHost() {
        return targetHost;
    }

    /**
     * 目标主机
     */
    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }

    /**
     * 目标URL
     */
    public String getTargetUrl() {
        return targetUrl;
    }

    /**
     * 目标URL
     */
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    /**
     * 检测时间
     */
    public Date getDetectionTime() {
        return detectionTime;
    }

    /**
     * 检测时间
     */
    public void setDetectionTime(Date detectionTime) {
        this.detectionTime = detectionTime;
    }

    /**
     * 是否存在风险：0-否，1-是
     */
    public Integer getIsRisky() {
        return isRisky;
    }

    /**
     * 是否存在风险：0-否，1-是
     */
    public void setIsRisky(Integer isRisky) {
        this.isRisky = isRisky;
    }

    /**
     * 风险详情
     */
    public String getRiskDetail() {
        return riskDetail;
    }

    /**
     * 风险详情
     */
    public void setRiskDetail(String riskDetail) {
        this.riskDetail = riskDetail;
    }

    /**
     * 响应内容（截取前1000字符）
     */
    public String getResponseContent() {
        return responseContent;
    }

    /**
     * 响应内容（截取前1000字符）
     */
    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }

    /**
     * 错误信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 错误信息
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 修复建议
     */
    public String getRemediationAdvice() {
        return remediationAdvice;
    }

    /**
     * 修复建议
     */
    public void setRemediationAdvice(String remediationAdvice) {
        this.remediationAdvice = remediationAdvice;
    }

    /**
     * 处理状态
     */
    public Object getStatus() {
        return status;
    }

    /**
     * 处理状态
     */
    public void setStatus(Object status) {
        this.status = status;
    }

    /**
     * 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 创建时间
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
        ApplicationRisk other = (ApplicationRisk) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRuleId() == null ? other.getRuleId() == null : this.getRuleId().equals(other.getRuleId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getRiskName() == null ? other.getRiskName() == null : this.getRiskName().equals(other.getRiskName()))
            && (this.getRiskType() == null ? other.getRiskType() == null : this.getRiskType().equals(other.getRiskType()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getTargetHost() == null ? other.getTargetHost() == null : this.getTargetHost().equals(other.getTargetHost()))
            && (this.getTargetUrl() == null ? other.getTargetUrl() == null : this.getTargetUrl().equals(other.getTargetUrl()))
            && (this.getDetectionTime() == null ? other.getDetectionTime() == null : this.getDetectionTime().equals(other.getDetectionTime()))
            && (this.getIsRisky() == null ? other.getIsRisky() == null : this.getIsRisky().equals(other.getIsRisky()))
            && (this.getRiskDetail() == null ? other.getRiskDetail() == null : this.getRiskDetail().equals(other.getRiskDetail()))
            && (this.getResponseContent() == null ? other.getResponseContent() == null : this.getResponseContent().equals(other.getResponseContent()))
            && (this.getErrorMessage() == null ? other.getErrorMessage() == null : this.getErrorMessage().equals(other.getErrorMessage()))
            && (this.getRemediationAdvice() == null ? other.getRemediationAdvice() == null : this.getRemediationAdvice().equals(other.getRemediationAdvice()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRuleId() == null) ? 0 : getRuleId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getRiskName() == null) ? 0 : getRiskName().hashCode());
        result = prime * result + ((getRiskType() == null) ? 0 : getRiskType().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getTargetHost() == null) ? 0 : getTargetHost().hashCode());
        result = prime * result + ((getTargetUrl() == null) ? 0 : getTargetUrl().hashCode());
        result = prime * result + ((getDetectionTime() == null) ? 0 : getDetectionTime().hashCode());
        result = prime * result + ((getIsRisky() == null) ? 0 : getIsRisky().hashCode());
        result = prime * result + ((getRiskDetail() == null) ? 0 : getRiskDetail().hashCode());
        result = prime * result + ((getResponseContent() == null) ? 0 : getResponseContent().hashCode());
        result = prime * result + ((getErrorMessage() == null) ? 0 : getErrorMessage().hashCode());
        result = prime * result + ((getRemediationAdvice() == null) ? 0 : getRemediationAdvice().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
        sb.append(", ruleId=").append(ruleId);
        sb.append(", mac=").append(mac);
        sb.append(", riskName=").append(riskName);
        sb.append(", riskType=").append(riskType);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", targetHost=").append(targetHost);
        sb.append(", targetUrl=").append(targetUrl);
        sb.append(", detectionTime=").append(detectionTime);
        sb.append(", isRisky=").append(isRisky);
        sb.append(", riskDetail=").append(riskDetail);
        sb.append(", responseContent=").append(responseContent);
        sb.append(", errorMessage=").append(errorMessage);
        sb.append(", remediationAdvice=").append(remediationAdvice);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append("]");
        return sb.toString();
    }
}