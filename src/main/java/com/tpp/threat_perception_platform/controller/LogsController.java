package com.tpp.threat_perception_platform.controller;

import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AcctChgLogService;
import com.tpp.threat_perception_platform.service.AuditLogService;
import com.tpp.threat_perception_platform.service.LoginActionService;
import com.tpp.threat_perception_platform.service.LoginLogService;
import com.tpp.threat_perception_platform.vo.LoginLogWithActionsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogsController {

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private AcctChgLogService acctChgLogService;
    @Autowired
    private LoginActionService loginActionService;

    @PostMapping("/logs/audit")
    public ResponseResult<List<LogParam>> getAllLoginLogsWithActions(@RequestBody LogParam param)
    {
        List<LogParam> logs = loginActionService.getLoginLogsWithActions(param); // 你可以在 Service 中实现这个方法
        return new ResponseResult<>((long) logs.size(), logs);
    }

    @PostMapping("/logs/audit/sync")
    public ResponseResult auditLogDiscovery(){
        return auditLogService.auditLogDiscovery();
    }

    @PostMapping("/logs/login")
    public ResponseResult getLoginLogo(@RequestBody LogParam param){
        return loginLogService.loginLogList(param);
    }

    @PostMapping("/logs/login/sync")
    public ResponseResult loginLogDiscovery(){
        return loginLogService.loginLogDiscovery();
    }

    @PostMapping("/logs/accountChange")
    public ResponseResult accountChangeLog(@RequestBody LogParam param){
        return acctChgLogService.listAcctChgLog(param);
    }

    @PostMapping("/logs/accountChange/sync")
    public ResponseResult accountChangeLogDiscovery(){
        return acctChgLogService.accountChangeLogDiscovery();
    }

}
