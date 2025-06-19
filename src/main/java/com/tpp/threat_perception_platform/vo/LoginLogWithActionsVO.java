package com.tpp.threat_perception_platform.vo;

import com.tpp.threat_perception_platform.pojo.LoginLog;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LoginLogWithActionsVO extends LoginLog {
    private List<AuditActionVO> actions;

    @Data
    public static class AuditActionVO {
        private Integer eventId;
        private Date timestamp;
        private String action;
        private String details;
    }
}

