package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 
 * @TableName app_info
 */
public class AppInfo {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String mac;

    /**
     * 
     */
    private String displayName;

    /**
     * 
     */
    private String installLocation;

    /**
     * 
     */
    private String uninstallString;

    /**
     * 
     */
    private Date collectTime;

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
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 
     */
    public String getInstallLocation() {
        return installLocation;
    }

    /**
     * 
     */
    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    /**
     * 
     */
    public String getUninstallString() {
        return uninstallString;
    }

    /**
     * 
     */
    public void setUninstallString(String uninstallString) {
        this.uninstallString = uninstallString;
    }

    /**
     * 
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * 
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
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
        AppInfo other = (AppInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getDisplayName() == null ? other.getDisplayName() == null : this.getDisplayName().equals(other.getDisplayName()))
            && (this.getInstallLocation() == null ? other.getInstallLocation() == null : this.getInstallLocation().equals(other.getInstallLocation()))
            && (this.getUninstallString() == null ? other.getUninstallString() == null : this.getUninstallString().equals(other.getUninstallString()))
            && (this.getCollectTime() == null ? other.getCollectTime() == null : this.getCollectTime().equals(other.getCollectTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getDisplayName() == null) ? 0 : getDisplayName().hashCode());
        result = prime * result + ((getInstallLocation() == null) ? 0 : getInstallLocation().hashCode());
        result = prime * result + ((getUninstallString() == null) ? 0 : getUninstallString().hashCode());
        result = prime * result + ((getCollectTime() == null) ? 0 : getCollectTime().hashCode());
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
        sb.append(", displayName=").append(displayName);
        sb.append(", installLocation=").append(installLocation);
        sb.append(", uninstallString=").append(uninstallString);
        sb.append(", collectTime=").append(collectTime);
        sb.append("]");
        return sb.toString();
    }
}