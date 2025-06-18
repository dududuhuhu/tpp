package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 主机登录行为记录表
 * @TableName login_action
 */
public class LoginAction {
    /**
     * 自增ID，主键
     */
    private Integer id;

    /**
     * 关联登录日志表的ID（外键）
     */
    private Integer loginLogId;

    /**
     * 事件编号（来自原始数据）
     */
    private Integer eventId;

    /**
     * 事件发生时间
     */
    private Date timestamp;

    /**
     * 用户行为动作类型，例如open_app、visit_url等
     */
    private String action;

    /**
     * 行为详情描述，如访问的网址、打开的应用名等
     */
    private String details;

    /**
     * 自增ID，主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增ID，主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 关联登录日志表的ID（外键）
     */
    public Integer getLoginLogId() {
        return loginLogId;
    }

    /**
     * 关联登录日志表的ID（外键）
     */
    public void setLoginLogId(Integer loginLogId) {
        this.loginLogId = loginLogId;
    }

    /**
     * 事件编号（来自原始数据）
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * 事件编号（来自原始数据）
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 事件发生时间
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * 事件发生时间
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 用户行为动作类型，例如open_app、visit_url等
     */
    public String getAction() {
        return action;
    }

    /**
     * 用户行为动作类型，例如open_app、visit_url等
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 行为详情描述，如访问的网址、打开的应用名等
     */
    public String getDetails() {
        return details;
    }

    /**
     * 行为详情描述，如访问的网址、打开的应用名等
     */
    public void setDetails(String details) {
        this.details = details;
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
        LoginAction other = (LoginAction) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getLoginLogId() == null ? other.getLoginLogId() == null : this.getLoginLogId().equals(other.getLoginLogId()))
            && (this.getEventId() == null ? other.getEventId() == null : this.getEventId().equals(other.getEventId()))
            && (this.getTimestamp() == null ? other.getTimestamp() == null : this.getTimestamp().equals(other.getTimestamp()))
            && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
            && (this.getDetails() == null ? other.getDetails() == null : this.getDetails().equals(other.getDetails()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getLoginLogId() == null) ? 0 : getLoginLogId().hashCode());
        result = prime * result + ((getEventId() == null) ? 0 : getEventId().hashCode());
        result = prime * result + ((getTimestamp() == null) ? 0 : getTimestamp().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getDetails() == null) ? 0 : getDetails().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginLogId=").append(loginLogId);
        sb.append(", eventId=").append(eventId);
        sb.append(", timestamp=").append(timestamp);
        sb.append(", action=").append(action);
        sb.append(", details=").append(details);
        sb.append("]");
        return sb.toString();
    }
}