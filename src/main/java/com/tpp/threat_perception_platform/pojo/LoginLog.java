package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 主机登录日志表
 * @TableName login_log
 */
public class LoginLog {
    /**
     * 自增ID，主键
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
     * 记录创建时间
     */
    private Date createTime;

    /**
     * 是否为危险账户：1表示是，0表示否
     */
    private Integer isRiskUser;

    /**
     * 是否在风险时间登录：1表示是，0表示否
     */
    private Integer isRiskTime;

    /**
     * 自增ID，主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增ID，主键
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
     * 记录创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 记录创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 是否为危险账户：1表示是，0表示否
     */
    public Integer getIsRiskUser() {
        return isRiskUser;
    }

    /**
     * 是否为危险账户：1表示是，0表示否
     */
    public void setIsRiskUser(Integer isRiskUser) {
        this.isRiskUser = isRiskUser;
    }

    /**
     * 是否在风险时间登录：1表示是，0表示否
     */
    public Integer getIsRiskTime() {
        return isRiskTime;
    }

    /**
     * 是否在风险时间登录：1表示是，0表示否
     */
    public void setIsRiskTime(Integer isRiskTime) {
        this.isRiskTime = isRiskTime;
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
        LoginLog other = (LoginLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
            && (this.getLogoffTime() == null ? other.getLogoffTime() == null : this.getLogoffTime().equals(other.getLogoffTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIsRiskUser() == null ? other.getIsRiskUser() == null : this.getIsRiskUser().equals(other.getIsRiskUser()))
            && (this.getIsRiskTime() == null ? other.getIsRiskTime() == null : this.getIsRiskTime().equals(other.getIsRiskTime()));
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
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIsRiskUser() == null) ? 0 : getIsRiskUser().hashCode());
        result = prime * result + ((getIsRiskTime() == null) ? 0 : getIsRiskTime().hashCode());
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
        sb.append(", createTime=").append(createTime);
        sb.append(", isRiskUser=").append(isRiskUser);
        sb.append(", isRiskTime=").append(isRiskTime);
        sb.append("]");
        return sb.toString();
    }
}