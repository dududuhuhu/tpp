package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface BaselineDetectService {
    ResponseResult saveBaselineDetect(BaselineDetect baselineDetect);

    ResponseResult baselineDetectList(BaselineDetectParam param);

    ResponseResult baselineDetectDiscovery();
}
