package com.tpp.threat_perception_platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AccChgReportService;
import com.tpp.threat_perception_platform.service.LoginActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 报告统一 Controller，处理账号变更等风险报告接口
 */
@RestController
@RequestMapping("/api/report")
public class ReportController {

    @Autowired
    private AccChgReportService accChgReportService;

    @Autowired
    private LoginActionService loginActionService;

    /**
     * 根据 MAC 地址生成账号变更风险分析报告
     *
     * @return 风险分析报告 JSON
     */
    @PostMapping("/acct-chg/analyze")
    public JSONObject analyzeAcctChgReportByMac(@RequestBody JSONObject body) {
        String mac = body.getString("mac");
        return accChgReportService.analyzeAndGenerateReportByMac(mac);
    }
    @PostMapping("/audit/analyze")
    public ResponseResult analyzeAudit(@RequestBody LogParam param) {
        loginActionService.saveLoginActionReport(param);
        return loginActionService.loginActionReportList(param);
    }

}
