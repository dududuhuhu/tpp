package com.tpp.threat_perception_platform.pojo;

/**
 * 系统风险检测规则表
 * @TableName system_risk_rules
 */
public class SystemRiskRules {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 风险名称
     */
    private String riskName;

    /**
     * 风险等级（1=低，2=中，3=高）
     */
    private Integer riskLevel;

    /**
     * 风险类型（如系统配置、注册表配置、网络配置）
     */
    private String riskType;

    /**
     * 检测方法（如 NTP、REG、CMD）
     */
    private String detectionMethod;

    /**
     * 检测路径（如注册表路径、命令）
     */
    private String rulePath;

    /**
     * 匹配值（如注册表中的值或命令的返回）
     */
    private String rulePayload;

    /**
     * 修复建议
     */
    private String remediationAdvice;

    /**
     * 是否启用
     */
    private Integer isActive;

    /**
     * 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键
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
     * 风险等级（1=低，2=中，3=高）
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * 风险等级（1=低，2=中，3=高）
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 风险类型（如系统配置、注册表配置、网络配置）
     */
    public String getRiskType() {
        return riskType;
    }

    /**
     * 风险类型（如系统配置、注册表配置、网络配置）
     */
    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    /**
     * 检测方法（如 NTP、REG、CMD）
     */
    public String getDetectionMethod() {
        return detectionMethod;
    }

    /**
     * 检测方法（如 NTP、REG、CMD）
     */
    public void setDetectionMethod(String detectionMethod) {
        this.detectionMethod = detectionMethod;
    }

    /**
     * 检测路径（如注册表路径、命令）
     */
    public String getRulePath() {
        return rulePath;
    }

    /**
     * 检测路径（如注册表路径、命令）
     */
    public void setRulePath(String rulePath) {
        this.rulePath = rulePath;
    }

    /**
     * 匹配值（如注册表中的值或命令的返回）
     */
    public String getRulePayload() {
        return rulePayload;
    }

    /**
     * 匹配值（如注册表中的值或命令的返回）
     */
    public void setRulePayload(String rulePayload) {
        this.rulePayload = rulePayload;
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
     * 是否启用
     */
    public Integer getIsActive() {
        return isActive;
    }

    /**
     * 是否启用
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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
        SystemRiskRules other = (SystemRiskRules) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRiskName() == null ? other.getRiskName() == null : this.getRiskName().equals(other.getRiskName()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getRiskType() == null ? other.getRiskType() == null : this.getRiskType().equals(other.getRiskType()))
            && (this.getDetectionMethod() == null ? other.getDetectionMethod() == null : this.getDetectionMethod().equals(other.getDetectionMethod()))
            && (this.getRulePath() == null ? other.getRulePath() == null : this.getRulePath().equals(other.getRulePath()))
            && (this.getRulePayload() == null ? other.getRulePayload() == null : this.getRulePayload().equals(other.getRulePayload()))
            && (this.getRemediationAdvice() == null ? other.getRemediationAdvice() == null : this.getRemediationAdvice().equals(other.getRemediationAdvice()))
            && (this.getIsActive() == null ? other.getIsActive() == null : this.getIsActive().equals(other.getIsActive()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRiskName() == null) ? 0 : getRiskName().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getRiskType() == null) ? 0 : getRiskType().hashCode());
        result = prime * result + ((getDetectionMethod() == null) ? 0 : getDetectionMethod().hashCode());
        result = prime * result + ((getRulePath() == null) ? 0 : getRulePath().hashCode());
        result = prime * result + ((getRulePayload() == null) ? 0 : getRulePayload().hashCode());
        result = prime * result + ((getRemediationAdvice() == null) ? 0 : getRemediationAdvice().hashCode());
        result = prime * result + ((getIsActive() == null) ? 0 : getIsActive().hashCode());
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
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", riskType=").append(riskType);
        sb.append(", detectionMethod=").append(detectionMethod);
        sb.append(", rulePath=").append(rulePath);
        sb.append(", rulePayload=").append(rulePayload);
        sb.append(", remediationAdvice=").append(remediationAdvice);
        sb.append(", isActive=").append(isActive);
        sb.append("]");
        return sb.toString();
    }
}