package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface ProcessInfoService {
    public int analyzeAndSaveProcessInfo(ProcessInfo processInfo);

    ResponseResult getByMac(String mac);

    ResponseResult processInfoList(MyParam param);
}