package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 补丁表：记录设备与补丁的对应关系
 * @TableName hotfix
 */
public class Hotfix {
    /**
     * 自增主键ID
     */
    private Integer id;

    /**
     * 设备MAC地址
     */
    private String mac;

    /**
     * 补丁编号
     */
    private String hotfixId;

    /**
     * 记录更新时间
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
     * 补丁编号
     */
    public String getHotfixId() {
        return hotfixId;
    }

    /**
     * 补丁编号
     */
    public void setHotfixId(String hotfixId) {
        this.hotfixId = hotfixId;
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
        Hotfix other = (Hotfix) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getHotfixId() == null ? other.getHotfixId() == null : this.getHotfixId().equals(other.getHotfixId()))
            && (this.getUpdatedTime() == null ? other.getUpdatedTime() == null : this.getUpdatedTime().equals(other.getUpdatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getHotfixId() == null) ? 0 : getHotfixId().hashCode());
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
        sb.append(", hotfixId=").append(hotfixId);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}