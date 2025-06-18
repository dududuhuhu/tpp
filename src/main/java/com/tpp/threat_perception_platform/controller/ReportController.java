package com.tpp.threat_perception_platform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.service.AccChgReportService;
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

    /**
     * 根据 MAC 地址生成账号变更风险分析报告
     *
     * @param mac MAC 地址
     * @return 风险分析报告 JSON
     */
    @GetMapping("/acct-chg/analyze")
    public JSONObject analyzeAcctChgReportByMac(@RequestParam("mac") String mac) {
        return accChgReportService.analyzeAndGenerateReportByMac(mac);
    }
}
