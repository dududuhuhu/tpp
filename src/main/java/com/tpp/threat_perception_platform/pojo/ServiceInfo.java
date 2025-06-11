package com.tpp.threat_perception_platform.pojo;

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
    private Integer hostId;

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
    public Integer getHostId() {
        return hostId;
    }

    /**
     * 
     */
    public void setHostId(Integer hostId) {
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
        ServiceInfo other = (ServiceInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getHostId() == null ? other.getHostId() == null : this.getHostId().equals(other.getHostId()))
            && (this.getPort() == null ? other.getPort() == null : this.getPort().equals(other.getPort()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getProtocol() == null ? other.getProtocol() == null : this.getProtocol().equals(other.getProtocol()))
            && (this.getProduct() == null ? other.getProduct() == null : this.getProduct().equals(other.getProduct()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getExtrainfo() == null ? other.getExtrainfo() == null : this.getExtrainfo().equals(other.getExtrainfo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getHostId() == null) ? 0 : getHostId().hashCode());
        result = prime * result + ((getPort() == null) ? 0 : getPort().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getProtocol() == null) ? 0 : getProtocol().hashCode());
        result = prime * result + ((getProduct() == null) ? 0 : getProduct().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getExtrainfo() == null) ? 0 : getExtrainfo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", hostId=").append(hostId);
        sb.append(", port=").append(port);
        sb.append(", name=").append(name);
        sb.append(", state=").append(state);
        sb.append(", protocol=").append(protocol);
        sb.append(", product=").append(product);
        sb.append(", version=").append(version);
        sb.append(", extrainfo=").append(extrainfo);
        sb.append("]");
        return sb.toString();
    }
}