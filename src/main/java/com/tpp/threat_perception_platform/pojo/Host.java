package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 主机表
 * @TableName host
 */
public class Host {
    /**
     * 自增ID
     */
    private Integer id;

    /**
     * 主机mac地址
     */
    private String macAddress;

    /**
     * 主机的名字
     */
    private String hostName;

    /**
     * 主机ip
     */
    private String ipAddress;

    /**
     * 主机系统类型
     */
    private String osType;

    /**
     * 主机操作系统名字
     */
    private String osName;

    /**
     * 主机操作系统版本
     */
    private String osVersion;

    /**
     * 主机操作系统位数
     */
    private String osBit;

    /**
     * 主机cpu名字
     */
    private String cpuName;

    /**
     * 主机内存大小
     */
    private String ram;

    /**
     * 主机状态：1表示在线；0表示下线
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 自增ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 主机mac地址
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * 主机mac地址
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * 主机的名字
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 主机的名字
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * 主机ip
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 主机ip
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 主机系统类型
     */
    public String getOsType() {
        return osType;
    }

    /**
     * 主机系统类型
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * 主机操作系统名字
     */
    public String getOsName() {
        return osName;
    }

    /**
     * 主机操作系统名字
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    /**
     * 主机操作系统版本
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * 主机操作系统版本
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * 主机操作系统位数
     */
    public String getOsBit() {
        return osBit;
    }

    /**
     * 主机操作系统位数
     */
    public void setOsBit(String osBit) {
        this.osBit = osBit;
    }

    /**
     * 主机cpu名字
     */
    public String getCpuName() {
        return cpuName;
    }

    /**
     * 主机cpu名字
     */
    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    /**
     * 主机内存大小
     */
    public String getRam() {
        return ram;
    }

    /**
     * 主机内存大小
     */
    public void setRam(String ram) {
        this.ram = ram;
    }

    /**
     * 主机状态：1表示在线；0表示下线
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 主机状态：1表示在线；0表示下线
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Host other = (Host) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMacAddress() == null ? other.getMacAddress() == null : this.getMacAddress().equals(other.getMacAddress()))
            && (this.getHostName() == null ? other.getHostName() == null : this.getHostName().equals(other.getHostName()))
            && (this.getIpAddress() == null ? other.getIpAddress() == null : this.getIpAddress().equals(other.getIpAddress()))
            && (this.getOsType() == null ? other.getOsType() == null : this.getOsType().equals(other.getOsType()))
            && (this.getOsName() == null ? other.getOsName() == null : this.getOsName().equals(other.getOsName()))
            && (this.getOsVersion() == null ? other.getOsVersion() == null : this.getOsVersion().equals(other.getOsVersion()))
            && (this.getOsBit() == null ? other.getOsBit() == null : this.getOsBit().equals(other.getOsBit()))
            && (this.getCpuName() == null ? other.getCpuName() == null : this.getCpuName().equals(other.getCpuName()))
            && (this.getRam() == null ? other.getRam() == null : this.getRam().equals(other.getRam()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMacAddress() == null) ? 0 : getMacAddress().hashCode());
        result = prime * result + ((getHostName() == null) ? 0 : getHostName().hashCode());
        result = prime * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());
        result = prime * result + ((getOsType() == null) ? 0 : getOsType().hashCode());
        result = prime * result + ((getOsName() == null) ? 0 : getOsName().hashCode());
        result = prime * result + ((getOsVersion() == null) ? 0 : getOsVersion().hashCode());
        result = prime * result + ((getOsBit() == null) ? 0 : getOsBit().hashCode());
        result = prime * result + ((getCpuName() == null) ? 0 : getCpuName().hashCode());
        result = prime * result + ((getRam() == null) ? 0 : getRam().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", macAddress=").append(macAddress);
        sb.append(", hostName=").append(hostName);
        sb.append(", ipAddress=").append(ipAddress);
        sb.append(", osType=").append(osType);
        sb.append(", osName=").append(osName);
        sb.append(", osVersion=").append(osVersion);
        sb.append(", osBit=").append(osBit);
        sb.append(", cpuName=").append(cpuName);
        sb.append(", ram=").append(ram);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}