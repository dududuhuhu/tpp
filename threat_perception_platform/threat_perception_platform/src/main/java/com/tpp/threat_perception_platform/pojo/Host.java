package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 
 * @TableName host
 */
public class Host {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String hostName;

    /**
     * 
     */
    private String ipAddress;

    /**
     * 
     */
    private String macAddress;

    /**
     * 
     */
    private String osType;

    /**
     * 
     */
    private String osName;

    /**
     * 
     */
    private String osVersion;

    /**
     * 
     */
    private String osBit;

    /**
     * 
     */
    private String cpuType;

    /**
     * 
     */
    private String ram;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * 
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * 
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 
     */
    public String getMacAddress() {
        return macAddress;
    }

    /**
     * 
     */
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    /**
     * 
     */
    public String getOsType() {
        return osType;
    }

    /**
     * 
     */
    public void setOsType(String osType) {
        this.osType = osType;
    }

    /**
     * 
     */
    public String getOsName() {
        return osName;
    }

    /**
     * 
     */
    public void setOsName(String osName) {
        this.osName = osName;
    }

    /**
     * 
     */
    public String getOsVersion() {
        return osVersion;
    }

    /**
     * 
     */
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    /**
     * 
     */
    public String getOsBit() {
        return osBit;
    }

    /**
     * 
     */
    public void setOsBit(String osBit) {
        this.osBit = osBit;
    }

    /**
     * 
     */
    public String getCpuType() {
        return cpuType;
    }

    /**
     * 
     */
    public void setCpuType(String cpuType) {
        this.cpuType = cpuType;
    }

    /**
     * 
     */
    public String getRam() {
        return ram;
    }

    /**
     * 
     */
    public void setRam(String ram) {
        this.ram = ram;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
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
            && (this.getHostName() == null ? other.getHostName() == null : this.getHostName().equals(other.getHostName()))
            && (this.getIpAddress() == null ? other.getIpAddress() == null : this.getIpAddress().equals(other.getIpAddress()))
            && (this.getMacAddress() == null ? other.getMacAddress() == null : this.getMacAddress().equals(other.getMacAddress()))
            && (this.getOsType() == null ? other.getOsType() == null : this.getOsType().equals(other.getOsType()))
            && (this.getOsName() == null ? other.getOsName() == null : this.getOsName().equals(other.getOsName()))
            && (this.getOsVersion() == null ? other.getOsVersion() == null : this.getOsVersion().equals(other.getOsVersion()))
            && (this.getOsBit() == null ? other.getOsBit() == null : this.getOsBit().equals(other.getOsBit()))
            && (this.getCpuType() == null ? other.getCpuType() == null : this.getCpuType().equals(other.getCpuType()))
            && (this.getRam() == null ? other.getRam() == null : this.getRam().equals(other.getRam()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHostName() == null) ? 0 : getHostName().hashCode());
        result = prime * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());
        result = prime * result + ((getMacAddress() == null) ? 0 : getMacAddress().hashCode());
        result = prime * result + ((getOsType() == null) ? 0 : getOsType().hashCode());
        result = prime * result + ((getOsName() == null) ? 0 : getOsName().hashCode());
        result = prime * result + ((getOsVersion() == null) ? 0 : getOsVersion().hashCode());
        result = prime * result + ((getOsBit() == null) ? 0 : getOsBit().hashCode());
        result = prime * result + ((getCpuType() == null) ? 0 : getCpuType().hashCode());
        result = prime * result + ((getRam() == null) ? 0 : getRam().hashCode());
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
        sb.append(", hostName=").append(hostName);
        sb.append(", ipAddress=").append(ipAddress);
        sb.append(", macAddress=").append(macAddress);
        sb.append(", osType=").append(osType);
        sb.append(", osName=").append(osName);
        sb.append(", osVersion=").append(osVersion);
        sb.append(", osBit=").append(osBit);
        sb.append(", cpuType=").append(cpuType);
        sb.append(", ram=").append(ram);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}