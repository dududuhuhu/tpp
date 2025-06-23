package com.tpp.threat_perception_platform.pojo;

import java.util.Objects;

/**
 * 
 * @TableName service_info
 */
public class ServiceInfo {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Long hostId;


    /**
     * 
     */
    private Integer port;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String state;

    /**
     * 
     */
    private String protocol;

    /**
     * 
     */
    private String product;

    /**
     * 
     */
    private String version;

    /**
     * 
     */
    private String extrainfo;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    private String mac;

    public Integer getIsHarmful() {
        return isHarmful;
    }

    public void setIsHarmful(Integer isHarmful) {
        this.isHarmful = isHarmful;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceInfo that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(hostId, that.hostId) && Objects.equals(port, that.port) && Objects.equals(name, that.name) && Objects.equals(state, that.state) && Objects.equals(protocol, that.protocol) && Objects.equals(product, that.product) && Objects.equals(version, that.version) && Objects.equals(extrainfo, that.extrainfo) && Objects.equals(isHarmful, that.isHarmful) && Objects.equals(harmfulKey, that.harmfulKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hostId, port, name, state, protocol, product, version, extrainfo, isHarmful, harmfulKey);
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "id=" + id +
                ", hostId=" + hostId +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", protocol='" + protocol + '\'' +
                ", product='" + product + '\'' +
                ", version='" + version + '\'' +
                ", extrainfo='" + extrainfo + '\'' +
                ", isHarmful=" + isHarmful +
                ", harmfulKey='" + harmfulKey + '\'' +
                '}';
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
    public Long getHostId() {
        return hostId;
    }

    /**
     * 
     */
    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    /**
     * 
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 
     */
    public void setPort(Integer port) {
        this.port = port;
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
    public String getState() {
        return state;
    }

    /**
     * 
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * 
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * 
     */
    public String getProduct() {
        return product;
    }

    /**
     * 
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * 
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 
     */
    public String getExtrainfo() {
        return extrainfo;
    }

    /**
     * 
     */
    public void setExtrainfo(String extrainfo) {
        this.extrainfo = extrainfo;
    }

}