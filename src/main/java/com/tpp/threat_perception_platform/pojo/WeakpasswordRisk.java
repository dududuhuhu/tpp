package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 弱密码风险用户表
 * @TableName weakpassword_risk
 */
public class WeakpasswordRisk {
    /**
     * 自增主键ID
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 是否弱密码，true表示弱密码
     */
    private Integer weak;

    /**
     * 密码（可为空）
     */
    private String password;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 自增主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 是否弱密码，true表示弱密码
     */
    public Integer getWeak() {
        return weak;
    }

    /**
     * 是否弱密码，true表示弱密码
     */
    public void setWeak(Integer weak) {
        this.weak = weak;
    }

    /**
     * 密码（可为空）
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码（可为空）
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * MAC地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * MAC地址
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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
        WeakpasswordRisk other = (WeakpasswordRisk) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getWeak() == null ? other.getWeak() == null : this.getWeak().equals(other.getWeak()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getWeak() == null) ? 0 : getWeak().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getUpdatedTime() == null) ? 0 : getUpdatedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", weak=").append(weak);
        sb.append(", password=").append(password);
        sb.append(", mac=").append(mac);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}