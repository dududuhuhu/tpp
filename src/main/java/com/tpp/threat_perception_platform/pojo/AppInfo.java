package com.tpp.threat_perception_platform.pojo;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppInfo appInfo)) return false;
        return Objects.equals(id, appInfo.id) && Objects.equals(mac, appInfo.mac) && Objects.equals(displayName, appInfo.displayName) && Objects.equals(installLocation, appInfo.installLocation) && Objects.equals(uninstallString, appInfo.uninstallString) && Objects.equals(collectTime, appInfo.collectTime) && Objects.equals(isHarmful, appInfo.isHarmful) && Objects.equals(harmfulKey, appInfo.harmfulKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mac, displayName, installLocation, uninstallString, collectTime, isHarmful, harmfulKey);
    }

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

    public Integer getIsHarmful() {
        return isHarmful;
    }

    public void setIsHarmful(Integer isHarmful) {
        this.isHarmful = isHarmful;
    }

    /**
     *
     */
    private Integer isHarmful;

    public String getHarmfulKey() {
        return harmfulKey;
    }

    public void setHarmfulKey(String harmfulKey) {
        this.harmfulKey = harmfulKey;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", displayName='" + displayName + '\'' +
                ", installLocation='" + installLocation + '\'' +
                ", uninstallString='" + uninstallString + '\'' +
                ", collectTime=" + collectTime +
                ", isHarmful=" + isHarmful +
                ", harmfulKey='" + harmfulKey + '\'' +
                '}';
    }

    /**
     *
     */
    private String harmfulKey;

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

}