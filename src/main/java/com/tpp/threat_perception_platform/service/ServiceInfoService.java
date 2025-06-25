package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.pojo.ServiceInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.Date;
import java.util.List;

public interface ServiceInfoService {

    public ResponseResult retrieveAssetsService(MyParam param);

    int analyzeAndSaveServiceInfo(ServiceInfo serviceInfo, Date now);

    public ResponseResult selectByHostId(Long hostId);
}
