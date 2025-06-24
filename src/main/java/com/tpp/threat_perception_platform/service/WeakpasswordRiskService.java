package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.WeakpasswordParam;
import com.tpp.threat_perception_platform.pojo.WeakpasswordRisk;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.sql.Timestamp;
import java.util.List;

public interface WeakpasswordRiskService {


    ResponseResult saveWeakpasswordRisk(WeakpasswordRisk weakpasswordRisk, Timestamp now);

    public ResponseResult weakpasswordList(WeakpasswordParam param);
}
