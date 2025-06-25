package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.sql.Timestamp;
import java.util.Date;

public interface ProcessInfoService {
    public int analyzeAndSaveProcessInfo(ProcessInfo processInfo, Date now);

    ResponseResult getByMac(String mac);

    ResponseResult processInfoList(MyParam param);
}