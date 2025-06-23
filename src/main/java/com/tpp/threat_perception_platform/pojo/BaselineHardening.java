package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 基线加固记录表
 * @TableName baseline_hardening
 */
public class BaselineHardening {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 客户端MAC地址
     */
    private String mac;

    /**
     * 基线加固项名称
     */
    private String name;

    /**
     * 加固结果（成功/失败）
     */
    private String result;

    /**
     * 记录更新时间
     */
    private Date updatedTime;

    /**
     * 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 客户端MAC地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * 客户端MAC地址
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 基线加固项名称
     */
    public String getName() {
        return name;
    }

    /**
     * 基线加固项名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 加固结果（成功/失败）
     */
    public String getResult() {
        return result;
    }

    /**
     * 加固结果（成功/失败）
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 记录更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 记录更新时间
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
        BaselineHardening other = (BaselineHardening) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
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
        sb.append(", mac=").append(mac);
        sb.append(", name=").append(name);
        sb.append(", result=").append(result);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}