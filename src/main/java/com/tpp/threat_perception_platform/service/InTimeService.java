package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.InTimeParam;
import com.tpp.threat_perception_platform.pojo.InTime;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.sql.Timestamp;

public interface InTimeService {
    ResponseResult saveInTime(InTime inTime, Timestamp now);

    ResponseResult inTimeList(InTimeParam param);

    ResponseResult inTimeDiscovery();
}
