package com.tpp.threat_perception_platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AccChgReportService;
import com.tpp.threat_perception_platform.service.ApplicationRiskService;
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

    @Autowired
    private ApplicationRiskService applicationRiskService;

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
        System.out.println("report:"+loginActionService.loginActionReportList(param));
        return loginActionService.loginActionReportList(param);
    }


    @PostMapping("audit/result")
    public ResponseResult Auditreport(@RequestBody LogParam param) {
        System.out.println("param:"+param);
        System.out.println("report:"+loginActionService.loginActionReportList(param));
        return loginActionService.loginActionReportList(param);
    }

    /**
     * 根据 MAC 地址分析应用风险记录并生成AI报告
     * @param mac 主机MAC地址，作为请求参数传入
     * @return 响应结果，包含报告内容
     */
    @GetMapping("/app-risk/analyze")
    public ResponseResult analyzeAppRiskReport(@RequestParam("mac") String mac) {
        return applicationRiskService.analyzeAndSaveAppRiskReport(mac);
    }
}
