package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 
 * @TableName account_info
 */
public class AccountInfo {
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
    private String name;

    /**
     * 
     */
    private String fullName;

    /**
     * 
     */
    private String sid;

    /**
     * 
     */
    private Integer sidType;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private Integer disabled;

    /**
     * 
     */
    private Integer lockout;

    /**
     * 
     */
    private Integer passwordChangeable;

    /**
     * 
     */
    private Integer passwordExpires;

    /**
     * 
     */
    private Integer passwordRequired;

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
    private Integer isHarmful;

    /**
     * 
     */
    private String harmfulKey;

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
    public String getName() {
        return name;
    }

    /**
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * 
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 
     */
    public String getSid() {
        return sid;
    }

    /**
     * 
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 
     */
    public Integer getSidType() {
        return sidType;
    }

    /**
     * 
     */
    public void setSidType(Integer sidType) {
        this.sidType = sidType;
    }

    /**
     * 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     */
    public Integer getDisabled() {
        return disabled;
    }

    /**
     * 
     */
    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    /**
     * 
     */
    public Integer getLockout() {
        return lockout;
    }

    /**
     * 
     */
    public void setLockout(Integer lockout) {
        this.lockout = lockout;
    }

    /**
     * 
     */
    public Integer getPasswordChangeable() {
        return passwordChangeable;
    }

    /**
     * 
     */
    public void setPasswordChangeable(Integer passwordChangeable) {
        this.passwordChangeable = passwordChangeable;
    }

    /**
     * 
     */
    public Integer getPasswordExpires() {
        return passwordExpires;
    }

    /**
     * 
     */
    public void setPasswordExpires(Integer passwordExpires) {
        this.passwordExpires = passwordExpires;
    }

    /**
     * 
     */
    public Integer getPasswordRequired() {
        return passwordRequired;
    }

    /**
     * 
     */
    public void setPasswordRequired(Integer passwordRequired) {
        this.passwordRequired = passwordRequired;
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

    /**
     * 
     */
    public Integer getIsHarmful() {
        return isHarmful;
    }

    /**
     * 
     */
    public void setIsHarmful(Integer isHarmful) {
        this.isHarmful = isHarmful;
    }

    /**
     * 
     */
    public String getHarmfulKey() {
        return harmfulKey;
    }

    /**
     * 
     */
    public void setHarmfulKey(String harmfulKey) {
        this.harmfulKey = harmfulKey;
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
        AccountInfo other = (AccountInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFullName() == null ? other.getFullName() == null : this.getFullName().equals(other.getFullName()))
            && (this.getSid() == null ? other.getSid() == null : this.getSid().equals(other.getSid()))
            && (this.getSidType() == null ? other.getSidType() == null : this.getSidType().equals(other.getSidType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDisabled() == null ? other.getDisabled() == null : this.getDisabled().equals(other.getDisabled()))
            && (this.getLockout() == null ? other.getLockout() == null : this.getLockout().equals(other.getLockout()))
            && (this.getPasswordChangeable() == null ? other.getPasswordChangeable() == null : this.getPasswordChangeable().equals(other.getPasswordChangeable()))
            && (this.getPasswordExpires() == null ? other.getPasswordExpires() == null : this.getPasswordExpires().equals(other.getPasswordExpires()))
            && (this.getPasswordRequired() == null ? other.getPasswordRequired() == null : this.getPasswordRequired().equals(other.getPasswordRequired()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()))
            && (this.getIsHarmful() == null ? other.getIsHarmful() == null : this.getIsHarmful().equals(other.getIsHarmful()))
            && (this.getHarmfulKey() == null ? other.getHarmfulKey() == null : this.getHarmfulKey().equals(other.getHarmfulKey()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFullName() == null) ? 0 : getFullName().hashCode());
        result = prime * result + ((getSid() == null) ? 0 : getSid().hashCode());
        result = prime * result + ((getSidType() == null) ? 0 : getSidType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDisabled() == null) ? 0 : getDisabled().hashCode());
        result = prime * result + ((getLockout() == null) ? 0 : getLockout().hashCode());
        result = prime * result + ((getPasswordChangeable() == null) ? 0 : getPasswordChangeable().hashCode());
        result = prime * result + ((getPasswordExpires() == null) ? 0 : getPasswordExpires().hashCode());
        result = prime * result + ((getPasswordRequired() == null) ? 0 : getPasswordRequired().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        result = prime * result + ((getIsHarmful() == null) ? 0 : getIsHarmful().hashCode());
        result = prime * result + ((getHarmfulKey() == null) ? 0 : getHarmfulKey().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", fullName=").append(fullName);
        sb.append(", sid=").append(sid);
        sb.append(", sidType=").append(sidType);
        sb.append(", status=").append(status);
        sb.append(", disabled=").append(disabled);
        sb.append(", lockout=").append(lockout);
        sb.append(", passwordChangeable=").append(passwordChangeable);
        sb.append(", passwordExpires=").append(passwordExpires);
        sb.append(", passwordRequired=").append(passwordRequired);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", isHarmful=").append(isHarmful);
        sb.append(", harmfulKey=").append(harmfulKey);
        sb.append("]");
        return sb.toString();
    }
}