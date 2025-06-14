package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 应用风险检测规则表
 * @TableName application_risk_rules
 */
public class ApplicationRiskRules {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 风险名称
     */
    private String riskName;

    /**
     * 风险描述
     */
    private String riskDesc;

    /**
     * 风险等级：1-低风险，2-中风险，3-高风险，4-严重风险
     */
    private Integer riskLevel;

    /**
     * 风险类型：权限配置、信息泄露、服务配置、认证绕过等
     */
    private String riskType;

    /**
     * 目标服务：tomcat、ftp、nginx、apache等
     */
    private String targetService;

    /**
     * 检测方法：HTTP、HTTPS、PORT、SERVICE
     */
    private String detectionMethod;

    /**
     * 检测路径
     */
    private String detectionPath;

    /**
     * 检测载荷
     */
    private String detectionPayload;

    /**
     * 风险标识：用于判断是否存在风险的关键字
     */
    private String riskFlag;

    /**
     * 修复建议
     */
    private String remediationAdvice;

    /**
     * 规则状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

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
     * 风险描述
     */
    public String getRiskDesc() {
        return riskDesc;
    }

    /**
     * 风险描述
     */
    public void setRiskDesc(String riskDesc) {
        this.riskDesc = riskDesc;
    }

    /**
     * 风险等级：1-低风险，2-中风险，3-高风险，4-严重风险
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * 风险等级：1-低风险，2-中风险，3-高风险，4-严重风险
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 风险类型：权限配置、信息泄露、服务配置、认证绕过等
     */
    public String getRiskType() {
        return riskType;
    }

    /**
     * 风险类型：权限配置、信息泄露、服务配置、认证绕过等
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    /**
     * 目标服务：tomcat、ftp、nginx、apache等
     */
    public String getTargetService() {
        return targetService;
    }

    /**
     * 目标服务：tomcat、ftp、nginx、apache等
     */
    public void setTargetService(String targetService) {
        this.targetService = targetService;
    }

    /**
     * 检测方法：HTTP、HTTPS、PORT、SERVICE
     */
    public String getDetectionMethod() {
        return detectionMethod;
    }

    /**
     * 检测方法：HTTP、HTTPS、PORT、SERVICE
     */
    public void setDetectionMethod(String detectionMethod) {
        this.detectionMethod = detectionMethod;
    }

    /**
     * 检测路径
     */
    public String getDetectionPath() {
        return detectionPath;
    }

    /**
     * 检测路径
     */
    public void setDetectionPath(String detectionPath) {
        this.detectionPath = detectionPath;
    }

    /**
     * 检测载荷
     */
    public String getDetectionPayload() {
        return detectionPayload;
    }

    /**
     * 检测载荷
     */
    public void setDetectionPayload(String detectionPayload) {
        this.detectionPayload = detectionPayload;
    }

    /**
     * 风险标识：用于判断是否存在风险的关键字
     */
    public String getRiskFlag() {
        return riskFlag;
    }

    /**
     * 风险标识：用于判断是否存在风险的关键字
     */
    public void setRiskFlag(String riskFlag) {
        this.riskFlag = riskFlag;
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
     * 规则状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 规则状态
     */
    public void setStatus(String status) {
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

    /**
     * 更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 更新时间
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
        ApplicationRiskRules other = (ApplicationRiskRules) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRiskName() == null ? other.getRiskName() == null : this.getRiskName().equals(other.getRiskName()))
            && (this.getRiskDesc() == null ? other.getRiskDesc() == null : this.getRiskDesc().equals(other.getRiskDesc()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getRiskType() == null ? other.getRiskType() == null : this.getRiskType().equals(other.getRiskType()))
            && (this.getTargetService() == null ? other.getTargetService() == null : this.getTargetService().equals(other.getTargetService()))
            && (this.getDetectionMethod() == null ? other.getDetectionMethod() == null : this.getDetectionMethod().equals(other.getDetectionMethod()))
            && (this.getDetectionPath() == null ? other.getDetectionPath() == null : this.getDetectionPath().equals(other.getDetectionPath()))
            && (this.getDetectionPayload() == null ? other.getDetectionPayload() == null : this.getDetectionPayload().equals(other.getDetectionPayload()))
            && (this.getRiskFlag() == null ? other.getRiskFlag() == null : this.getRiskFlag().equals(other.getRiskFlag()))
            && (this.getRemediationAdvice() == null ? other.getRemediationAdvice() == null : this.getRemediationAdvice().equals(other.getRemediationAdvice()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRiskName() == null) ? 0 : getRiskName().hashCode());
        result = prime * result + ((getRiskDesc() == null) ? 0 : getRiskDesc().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getRiskType() == null) ? 0 : getRiskType().hashCode());
        result = prime * result + ((getTargetService() == null) ? 0 : getTargetService().hashCode());
        result = prime * result + ((getDetectionMethod() == null) ? 0 : getDetectionMethod().hashCode());
        result = prime * result + ((getDetectionPath() == null) ? 0 : getDetectionPath().hashCode());
        result = prime * result + ((getDetectionPayload() == null) ? 0 : getDetectionPayload().hashCode());
        result = prime * result + ((getRiskFlag() == null) ? 0 : getRiskFlag().hashCode());
        result = prime * result + ((getRemediationAdvice() == null) ? 0 : getRemediationAdvice().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
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
        sb.append(", riskName=").append(riskName);
        sb.append(", riskDesc=").append(riskDesc);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", riskType=").append(riskType);
        sb.append(", targetService=").append(targetService);
        sb.append(", detectionMethod=").append(detectionMethod);
        sb.append(", detectionPath=").append(detectionPath);
        sb.append(", detectionPayload=").append(detectionPayload);
        sb.append(", riskFlag=").append(riskFlag);
        sb.append(", remediationAdvice=").append(remediationAdvice);
        sb.append(", status=").append(status);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}