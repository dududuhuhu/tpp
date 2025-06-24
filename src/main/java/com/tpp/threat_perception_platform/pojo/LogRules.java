package com.tpp.threat_perception_platform.pojo;

/**
 * 
 * @TableName log_rules
 */
public class LogRules {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 适用平台
     */
    private String platform;

    /**
     * 事件ID或匹配字段
     */
    private String eventId;

    /**
     * 1~10表示风险等级
     */
    private Integer riskLevel;

    /**
     * 规则描述
     */
    private String description;

    /**
     * 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 适用平台
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * 适用平台
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * 事件ID或匹配字段
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * 事件ID或匹配字段
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * 1~10表示风险等级
     */
    public Integer getRiskLevel() {
        return riskLevel;
    }

    /**
     * 1~10表示风险等级
     */
    public void setRiskLevel(Integer riskLevel) {
        this.riskLevel = riskLevel;
    }

    /**
     * 规则描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 规则描述
     */
    public void setDescription(String description) {
        this.description = description;
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
        LogRules other = (LogRules) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPlatform() == null ? other.getPlatform() == null : this.getPlatform().equals(other.getPlatform()))
            && (this.getEventId() == null ? other.getEventId() == null : this.getEventId().equals(other.getEventId()))
            && (this.getRiskLevel() == null ? other.getRiskLevel() == null : this.getRiskLevel().equals(other.getRiskLevel()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlatform() == null) ? 0 : getPlatform().hashCode());
        result = prime * result + ((getEventId() == null) ? 0 : getEventId().hashCode());
        result = prime * result + ((getRiskLevel() == null) ? 0 : getRiskLevel().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", platform=").append(platform);
        sb.append(", eventId=").append(eventId);
        sb.append(", riskLevel=").append(riskLevel);
        sb.append(", description=").append(description);
        sb.append("]");
        return sb.toString();
    }
}