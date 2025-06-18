package com.tpp.threat_perception_platform.pojo;

import java.util.Date;

/**
 * 账号变更日志表
 * @TableName acct_chg_log
 */
public class AcctChgLog {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 主机MAC地址
     */
    private String mac;

    /**
     * 事件ID
     */
    private Integer eventId;

    /**
     * 事件时间
     */
    private Date eventTime;

    /**
     * 操作行为描述
     */
    private String action;

    /**
     * 被操作用户
     */
    private String targetUser;

    /**
     * 操作用户
     */
    private String operatorUser;

    /**
     * 记录创建时间
     */
    private Date createdAt;

    /**
     * 记录更新时间
     */
    private Date updatedAt;

    /**
     * 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 主机MAC地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * 主机MAC地址
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 事件ID
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * 事件ID
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 事件时间
     */
    public Date getEventTime() {
        return eventTime;
    }

    /**
     * 事件时间
     */
    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * 操作行为描述
     */
    public String getAction() {
        return action;
    }

    /**
     * 操作行为描述
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * 被操作用户
     */
    public String getTargetUser() {
        return targetUser;
    }

    /**
     * 被操作用户
     */
    public void setTargetUser(String targetUser) {
        this.targetUser = targetUser;
    }

    /**
     * 操作用户
     */
    public String getOperatorUser() {
        return operatorUser;
    }

    /**
     * 操作用户
     */
    public void setOperatorUser(String operatorUser) {
        this.operatorUser = operatorUser;
    }

    /**
     * 记录创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 记录创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 记录更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 记录更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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
        AcctChgLog other = (AcctChgLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getEventId() == null ? other.getEventId() == null : this.getEventId().equals(other.getEventId()))
            && (this.getEventTime() == null ? other.getEventTime() == null : this.getEventTime().equals(other.getEventTime()))
            && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
            && (this.getTargetUser() == null ? other.getTargetUser() == null : this.getTargetUser().equals(other.getTargetUser()))
            && (this.getOperatorUser() == null ? other.getOperatorUser() == null : this.getOperatorUser().equals(other.getOperatorUser()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getEventId() == null) ? 0 : getEventId().hashCode());
        result = prime * result + ((getEventTime() == null) ? 0 : getEventTime().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getTargetUser() == null) ? 0 : getTargetUser().hashCode());
        result = prime * result + ((getOperatorUser() == null) ? 0 : getOperatorUser().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
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
        sb.append(", eventId=").append(eventId);
        sb.append(", eventTime=").append(eventTime);
        sb.append(", action=").append(action);
        sb.append(", targetUser=").append(targetUser);
        sb.append(", operatorUser=").append(operatorUser);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append("]");
        return sb.toString();
    }
}