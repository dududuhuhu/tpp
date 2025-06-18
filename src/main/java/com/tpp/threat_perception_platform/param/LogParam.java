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

    private Boolean isRiskUser;      // 是否危险账户（登录日志）
    private Boolean isRiskTime;      // 是否危险时间（登录日志）

    private Integer page;  // 当前页码
    private Integer limit; // 每页大小

    private List<Action> actions;    // 操作事件列表（审计日志和账号变更日志共用）

    // 操作事件中的字段，对应不同日志可能有不同含义
    public static class Action {

        private String eventId;        // 事件ID

        public void setEventId(String eventId) {
            this.eventId = eventId;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        private Date timestamp;        // 时间戳

        public void setAction(String action) {
            this.action = action;
        }

        private String action;         // 操作行为描述

        public void setDetails(String details) {
            this.details = details;
        }

        private String details;        // 细节（审计日志）

        public void setTargetUser(String targetUser) {
            this.targetUser = targetUser;
        }

        private String targetUser;     // 目标用户（账号变更日志）

        public void setOperatorUser(String operatorUser) {
            this.operatorUser = operatorUser;
        }

        private String operatorUser;   // 操作用户（账号变更日志）

        public String getEventId() {
            return eventId;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public String getAction() {
            return action;
        }

        public String getDetails() {
            return details;
        }

        public String getTargetUser() {
            return targetUser;
        }

        public String getOperatorUser() {
            return operatorUser;
        }

        // 省略getter/setter，实际写时加上
    }

    // 省略getter/setter，实际写时加上

}
