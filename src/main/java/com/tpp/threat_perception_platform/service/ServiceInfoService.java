package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.pojo.ServiceInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface ServiceInfoService {

    public int analyzeAndSaveServiceInfo(ServiceInfo serviceInfo);

    public ResponseResult retrieveAssetsService(MyParam param);

    public ResponseResult selectByHostId(Long hostId);
}
