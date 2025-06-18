package com.tpp.threat_perception_platform.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogParam {

    private String mac;              // 设备MAC地址
    private String username;         // 用户名，登录名（可能为空，部分日志无具体用户名）

    private Date loginTime;          // 登录时间（登录日志、审计日志）
    private Date logoffTime;         // 注销时间（审计日志）

    private Integer isRiskUser;      // 是否危险账户（登录日志）
    private Integer isRiskTime;      // 是否危险时间（登录日志）

    private List<Action> actions;    // 操作事件列表（审计日志和账号变更日志共用）

    /**
     * 页码
     */
    private Integer page;
    /**
     * 限制
     */
    private Integer limit;

    // 操作事件中的字段，对应不同日志可能有不同含义
    public static class Action {
        private Integer eventId;        // 事件ID
        private Date timestamp;        // 时间戳
        private String action;         // 操作行为描述
        private String details;        // 细节（审计日志）
        private String targetUser;     // 目标用户（账号变更日志）
        private String operatorUser;   // 操作用户（账号变更日志）

        // 省略getter/setter，实际写时加上


        public Integer getEventId() {
            return eventId;
        }

        public void setEventId(Integer eventId) {
            this.eventId = eventId;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getTargetUser() {
            return targetUser;
        }

        public void setTargetUser(String targetUser) {
            this.targetUser = targetUser;
        }

        public String getOperatorUser() {
            return operatorUser;
        }

        public void setOperatorUser(String operatorUser) {
            this.operatorUser = operatorUser;
        }
    }

    // 省略getter/setter，实际写时加上

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoffTime() {
        return logoffTime;
    }

    public void setLogoffTime(Date logoffTime) {
        this.logoffTime = logoffTime;
    }

    public Integer getIsRiskUser() {
        return isRiskUser;
    }

    public void setIsRiskUser(Integer isRiskUser) {
        this.isRiskUser = isRiskUser;
    }

    public Integer getIsRiskTime() {
        return isRiskTime;
    }

    public void setIsRiskTime(Integer isRiskTime) {
        this.isRiskTime = isRiskTime;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
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
