package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.sql.Timestamp;

public interface BaselineDetectService {
    ResponseResult saveBaselineDetect(BaselineDetect baselineDetect, Timestamp now);

    ResponseResult baselineDetectList(BaselineDetectParam param);

    ResponseResult baselineDetectDiscovery(BaselineTask task);
}
