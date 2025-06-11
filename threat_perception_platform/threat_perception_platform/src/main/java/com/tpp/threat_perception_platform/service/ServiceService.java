package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Service;
import com.tpp.threat_perception_platform.response.ResponseResult;
import org.apache.coyote.Response;

import java.util.List;

public interface ServiceService {
    public Integer saveService(String macAddress, List<Service> serviceList);

    public ResponseResult retrieveAssetsService(MyParam param);

    public ResponseResult selectByHostId(Long hostId);
}
