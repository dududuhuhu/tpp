package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * AI用户行为分析结果表
 * @TableName login_action_report
 */
public class LoginActionReport {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 主机MAC地址
     */
    private String mac;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 用户登录时间
     */
    private Date loginTime;

    /**
     * 用户注销时间
     */
    private Date logoffTime;

    /**
     * 风险等级：High / Medium / Low / Safe
     */
    private String riskLevel;

    /**
     * 是否可疑：1是，0否
     */
    private Integer suspicious;

    /**
     * AI判断的理由说明
     */
    private String reason;

    /**
     * 用于分析的提示词内容
     */
    private String aiPrompt;

    /**
     * AI的原始返回内容
     */
    private String aiRawOutput;

    /**
     * 分析生成时间
     */
    private Date createTime;

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
     * 主机MAC地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * 主机MAC地址
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 登录用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 登录用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 用户登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 用户登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 用户注销时间
     */
    public Date getLogoffTime() {
        return logoffTime;
    }

    /**
     * 用户注销时间
     */
    public void setLogoffTime(Date logoffTime) {
        this.logoffTime = logoffTime;
    }

    /**
     * 风险等级：High / Medium / Low / Safe
     */
    public String getRiskLevel() {
        return riskLevel;
    }

    /**
     * 风险等级：High / Medium / Low / Safe
     */
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 是否可疑：1是，0否
     */
    public Integer getSuspicious() {
        return suspicious;
    }

    /**
     * 是否可疑：1是，0否
     */
    public void setSuspicious(Integer suspicious) {
        this.suspicious = suspicious;
    }

    /**
     * AI判断的理由说明
     */
    public String getReason() {
        return reason;
    }

    /**
     * AI判断的理由说明
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 用于分析的提示词内容
     */
    public String getAiPrompt() {
        return aiPrompt;
    }

    /**
     * 用于分析的提示词内容
     */
    public void setAiPrompt(String aiPrompt) {
        this.aiPrompt = aiPrompt;
    }

    /**
     * AI的原始返回内容
     */
    public String getAiRawOutput() {
        return aiRawOutput;
    }

    /**
     * AI的原始返回内容
     */
    public void setAiRawOutput(String aiRawOutput) {
        this.aiRawOutput = aiRawOutput;
    }

    /**
     * 分析生成时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 分析生成时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        LoginActionReport other = (LoginActionReport) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
            && (this.getLogoffTime() == null ? other.getLogoffTime() == null : this.getLogoffTime().equals(other.getLogoffTime()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getSuspicious() == null ? other.getSuspicious() == null : this.getSuspicious().equals(other.getSuspicious()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getAiPrompt() == null ? other.getAiPrompt() == null : this.getAiPrompt().equals(other.getAiPrompt()))
            && (this.getAiRawOutput() == null ? other.getAiRawOutput() == null : this.getAiRawOutput().equals(other.getAiRawOutput()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getLogoffTime() == null) ? 0 : getLogoffTime().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getSuspicious() == null) ? 0 : getSuspicious().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getAiPrompt() == null) ? 0 : getAiPrompt().hashCode());
        result = prime * result + ((getAiRawOutput() == null) ? 0 : getAiRawOutput().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
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
        sb.append(", username=").append(username);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", logoffTime=").append(logoffTime);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", suspicious=").append(suspicious);
        sb.append(", reason=").append(reason);
        sb.append(", aiPrompt=").append(aiPrompt);
        sb.append(", aiRawOutput=").append(aiRawOutput);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}