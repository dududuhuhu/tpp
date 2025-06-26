package com.tpp.threat_perception_platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.param.*;
import com.tpp.threat_perception_platform.dao.HotfixRiskAiReportMapper;
import com.tpp.threat_perception_platform.param.HotfixParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private HotfixService hotfixService;

    @Autowired
    private VulnerabilityService vulnerabilityService;

    @Autowired
    private SystemRiskService systemRiskService;

    @Autowired
    private BaselineDetectService baselineDetectService;



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
//    @PostMapping("/hotfix/analyze")
//    public ResponseResult analyzeHotfix(@RequestBody HotfixParam param) {
//        String mac=param.getMacAddress();
//        System.out.println("mac:"+mac);
//        return new ResponseResult(0,"success");
//    }


    /**
     * 根据 MAC 地址分析应用风险记录并生成AI报告
     * @return 响应结果，包含报告内容
     */
    @PostMapping("/app-risk/analyze")
    public ResponseResult analyzeAppRiskReport(@RequestBody ApplicationRiskParam param) {
        String mac = param.getMacAddress();
        System.out.println("vulreportMac:"+mac);
        return applicationRiskService.analyzeAndSaveAppRiskReport(mac);
    }

    /**
     * 根据 MAC 地址分析风险记录并生成AI报告
     * @param param，作为请求参数传入
     * @return 响应结果，包含报告内容
     */
    @PostMapping("/hotfix-risk/analyze")
    public ResponseResult analyzeHotfixRiskReport(@RequestBody HotfixParam param) {
        List<Hotfix> hotfixList = hotfixService.hotfixList(param).getData();
        return hotfixService.analyzeAndSaveHotfixRiskReport(hotfixList, param.getMacAddress());
    }

    /**
     * 根据 MAC 地址分析风险记录并生成AI报告
     * @return 响应结果，包含报告内容
     */
    @PostMapping("/vulnerability-risk/analyze")
    public ResponseResult analyzeVulnerabilityRiskReport(@RequestBody VulnerabilityParam param) {
        String mac = param.getMacAddress();
        System.out.println("vulreportMac:"+mac);
        return vulnerabilityService.analyzeAndSaveVulnerabilityRiskReport(mac);
    }

    /**
     * 根据 MAC 地址分析系统风险记录并生成AI报告
     * @return 响应结果，包含报告内容
     */
    @PostMapping("/system-risk/analyze")
    public ResponseResult analyzeSystemRiskReport(@RequestBody SystemRiskParam param) {
        String mac = param.getMacAddress();
        System.out.println("sysreportMac:"+mac);
        return systemRiskService.analyzeAndSaveSystemRiskReport(mac);
    }

    /**
     * 根据 MAC 地址分析基线探测记录并生成AI报告
     * @param mac JSON请求体，包含 mac 字段
     * @return 响应结果，包含分析报告内容
     */
    @PostMapping("/baselineDetect/analyze")
    public ResponseResult analyzeBaselineDetectReport(@RequestParam("mac") String mac) {
        System.out.println("BaselineDetectAnalyzeMac: " + mac);
        return baselineDetectService.analyzeAndSaveBaselineDetectReport(mac);
    }


    @PostMapping("/baseline/analyze")
    public ResponseResult analyzeBaselineReport(@RequestBody SystemRiskParam param) {
        String mac = param.getMacAddress();
        System.out.println("sysreportMac:"+mac);
        return systemRiskService.analyzeAndSaveSystemRiskReport(mac);
    }


}
