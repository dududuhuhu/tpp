package com.tpp.threat_perception_platform.service;

import com.alibaba.fastjson.JSONObject;
import com.tpp.threat_perception_platform.param.ReportParam;
import java.util.List;

public interface AccChgReportService {

    /**
     * 根据日志 ID 分析并生成风险报告
     * @param mac
     * @return 分析后的风险报告参数对象
     */
    JSONObject analyzeAndGenerateReportByMac(String mac);




}
