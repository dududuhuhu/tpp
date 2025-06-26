package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface BaselineDetectService {
    ResponseResult saveBaselineDetect(BaselineDetect baselineDetect);

    ResponseResult baselineDetectList(BaselineDetectParam param);

    ResponseResult baselineDetectDiscovery();

    /**
     * 根据mac分析基线探测记录并生成AI报告
     * @param mac 主机MAC地址
     * @return 响应结果，包含报告内容
     */
    ResponseResult analyzeAndSaveBaselineDetectReport(String mac);
}
