package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.ServiceMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private ServiceMapper serviceMapper;
    @Autowired
    private HostMapper hostMapper;

    @Override
    public Integer saveService(String macAddress, List<com.tpp.threat_perception_platform.pojo.Service> serviceList) {
        Host dbHost = hostMapper.selectByMacAddress(macAddress);
        if (dbHost == null) return 0;

        for (var service : serviceList) {
            service.setHostId(dbHost.getId());
        }
        System.out.println(serviceList);
        serviceMapper.deleteByHostId(Long.valueOf(dbHost.getId()));
        return serviceMapper.insertBatch(serviceList, Long.valueOf(dbHost.getId()));
    }

    @Override
    public ResponseResult selectByHostId(Long hostId) {
        List<com.tpp.threat_perception_platform.pojo.Service> serviceList = serviceMapper.selectByHostId(hostId);
        if (!serviceList.isEmpty()) serviceMapper.deleteByHostId(hostId);
        return new ResponseResult<>(0, serviceList);
    }

    @Override
    public ResponseResult retrieveAssetsService(MyParam param) {
        if (param.getId() == null) return new ResponseResult<>(400, "Invalid param");
        PageHelper.startPage(param.getPage(), param.getLimit());

        List<com.tpp.threat_perception_platform.pojo.Service> serviceList = serviceMapper.selectByHostId(Long.valueOf(param.getId()));
        if (!serviceList.isEmpty()) serviceMapper.deleteByHostId(Long.valueOf(param.getId()));
        return new ResponseResult<>(0, serviceList);
    }
}
