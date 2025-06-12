package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.ServiceInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.ServiceInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;
    @Autowired
    private HostMapper hostMapper;

    @Override
    public Integer saveService(String macAddress, List<com.tpp.threat_perception_platform.pojo.ServiceInfo> serviceList) {
        Host dbHost = hostMapper.selectByMacAddress(macAddress);
        if (dbHost == null) return 0;

        for (var service : serviceList) {
            service.setHostId(dbHost.getId());
        }
        System.out.println(serviceList);
        serviceInfoMapper.deleteByHostId(Long.valueOf(dbHost.getId()));
        return serviceInfoMapper.insertBatch(serviceList, Long.valueOf(dbHost.getId()));
    }

    @Override
    public ResponseResult selectByHostId(Long hostId) {
        List<com.tpp.threat_perception_platform.pojo.ServiceInfo> serviceList = serviceInfoMapper.selectByHostId(hostId);
        if (!serviceList.isEmpty()) serviceInfoMapper.deleteByHostId(hostId);
        return new ResponseResult<>(0, serviceList);
    }

    @Override
    public ResponseResult retrieveAssetsService(MyParam param) {
        String mac= param.getMacAddress();
        Integer id = hostMapper.selectByMacAddress(mac).getId();
        PageHelper.startPage(param.getPage(), param.getLimit());

        List<com.tpp.threat_perception_platform.pojo.ServiceInfo> serviceList = serviceInfoMapper.selectByHostId(Long.valueOf(id));
        if (!serviceList.isEmpty()) serviceInfoMapper.deleteByHostId(Long.valueOf(id));
        return new ResponseResult<>(0, serviceList);
    }
}
