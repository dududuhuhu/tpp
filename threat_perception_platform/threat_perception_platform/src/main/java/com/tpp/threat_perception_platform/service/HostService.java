package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.AssetParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface HostService {

    public int saveHost(Host host);

    public ResponseResult listHost(MyParam param);

    public ResponseResult deleteHost(Long[] ids);

    public int updateStatus(String macAddress);

    public ResponseResult assetsDiscovery(AssetParam param);
}
