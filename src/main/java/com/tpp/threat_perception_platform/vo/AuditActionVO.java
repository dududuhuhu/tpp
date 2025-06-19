package com.tpp.threat_perception_platform.vo;

import com.tpp.threat_perception_platform.pojo.LoginAction;
import lombok.Data;

@Data
public class AuditActionVO {
    private Integer event_id;
    private String timestamp;
    private String action;
    private String details;

    // 构造器或工具方法：从 LoginAction 构造
    public static AuditActionVO from(LoginAction action) {
        AuditActionVO vo = new AuditActionVO();
        vo.setEvent_id(action.getEventId());
        vo.setTimestamp(action.getTimestamp().toString()); // 可根据需要格式化
        vo.setAction(action.getAction());
        vo.setDetails(action.getDetails());
        return vo;
    }
}
