package com.tpp.threat_perception_platform.param;

import java.util.List;

public class WeakpasswordParam {
    private String hostName;

    private String macAddress;

    private String type;

    private String ipAddress;

    public List<String> getWeakPasswords() {
        return weakPasswords;
    }

    public void setWeakPasswords(List<String> weakPasswords) {
        this.weakPasswords = weakPasswords;
    }

    private List<String> weakPasswords;

    /**
     * 页码
     */
    private Integer page;
    /**
     * 限制
     */
    private Integer limit;

    public WeakpasswordParam(){

    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
