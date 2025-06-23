package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.sql.Timestamp;

public interface ProcessInfoService {
    ResponseResult save(ProcessInfo processInfo, Timestamp now);

    ResponseResult getByMac(String mac);

    ResponseResult processInfoList(MyParam param);
}