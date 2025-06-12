package com.tpp.threat_perception_platform.param;

public class AssetsParam {
    private String hostName;

    private String macAddress;

    private String type;

    private Integer account=0;

    private Integer service=0;

    private Integer process=0;

    private Integer app=0;

    public AssetsParam(){

    }
    public String getHostName(){
        return hostName;
    }

    public String getMacAddress() {
        return macAddress;
    }
    public String getType() {
        return type;
    }
    public Integer getAccount() {
        return account;
    }
    public Integer getService() {
        return service;
    }
    public Integer getProcess() {
        return process;
    }
    public Integer getApp() {
        return app;
    }

    public void setType(String assets) {
        this.type = assets;
    }
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
    public void setAccount(Integer account) {
        this.account = account;
    }
    public void setService(Integer service) {
        this.service = service;
    }
    public void setProcess(Integer process) {
        this.process = process;
    }
    public void setApp(Integer app) {
        this.app = app;
    }

}
