package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 
 * @TableName baseline_detect
 */
public class BaselineDetect {
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
    private String name;

    /**
     * 
     */
    private String result;

    /**
     * 
     */
    private String currentValue;

    /**
     * 
     */
    private String recommendedValue;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Date updatedTime;

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
    public String getResult() {
        return result;
    }

    /**
     * 
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * 
     */
    public String getCurrentValue() {
        return currentValue;
    }

    /**
     * 
     */
    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    /**
     * 
     */
    public String getRecommendedValue() {
        return recommendedValue;
    }

    /**
     * 
     */
    public void setRecommendedValue(String recommendedValue) {
        this.recommendedValue = recommendedValue;
    }

    /**
     * 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 
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
        BaselineDetect other = (BaselineDetect) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getCurrentValue() == null ? other.getCurrentValue() == null : this.getCurrentValue().equals(other.getCurrentValue()))
            && (this.getRecommendedValue() == null ? other.getRecommendedValue() == null : this.getRecommendedValue().equals(other.getRecommendedValue()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
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
        result = prime * result + ((getCurrentValue() == null) ? 0 : getCurrentValue().hashCode());
        result = prime * result + ((getRecommendedValue() == null) ? 0 : getRecommendedValue().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
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
        sb.append(", currentValue=").append(currentValue);
        sb.append(", recommendedValue=").append(recommendedValue);
        sb.append(", description=").append(description);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append("]");
        return sb.toString();
    }
}