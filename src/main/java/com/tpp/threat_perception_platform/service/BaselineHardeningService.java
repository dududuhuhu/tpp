package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.param.BaselineHardenParam;
import com.tpp.threat_perception_platform.pojo.BaselineHardening;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface BaselineHardeningService {
    ResponseResult saveBaselineHardening(BaselineHardening baselineHardening);

    ResponseResult baselineHardeningList(BaselineDetectParam param);

    ResponseResult baselineHardeningDiscovery(BaselineHardenParam param);
}
