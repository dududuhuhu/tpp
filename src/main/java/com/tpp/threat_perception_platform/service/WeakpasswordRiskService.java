package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.WeakpasswordParam;
import com.tpp.threat_perception_platform.pojo.WeakpasswordRisk;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface WeakpasswordRiskService {

    public ResponseResult saveWeakpasswordRisk(WeakpasswordRisk weakpasswordRisk);

    public ResponseResult weakpasswordList(WeakpasswordParam param);
}
