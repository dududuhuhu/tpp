package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.ServiceInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.ServiceInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;
    @Autowired
    private HostMapper hostMapper;

    @Override
    public Integer saveService(String macAddress, List<ServiceInfo> serviceList) {
        Host dbHost = hostMapper.selectByMacAddress(macAddress);
        if (dbHost == null) return 0;

        Date now = new Date();
        for (var service : serviceList) {
            service.setHostId(dbHost.getId());
            service.setDetectTime(now);  // 设置更新时间
        }

        return serviceInfoMapper.insertBatch(serviceList, Long.valueOf(dbHost.getId()));
    }

    @Override
    public ResponseResult selectByHostId(Long hostId) {
        List<com.tpp.threat_perception_platform.pojo.ServiceInfo> serviceList = serviceInfoMapper.selectByHostId(hostId);
        return new ResponseResult<>(0, serviceList);
    }

    @Override
    public ResponseResult retrieveAssetsService(MyParam param) {
        String mac = param.getMacAddress();
        Integer id = hostMapper.selectByMacAddress(mac).getId();

        // 在查询前设置分页
        PageHelper.startPage(param.getPage(), param.getLimit());

        // 查询方法会被分页插件拦截
        List<ServiceInfo> serviceList = serviceInfoMapper.selectByHostId(Long.valueOf(id));

        // PageHelper 会自动分页并计算 total
        PageInfo<ServiceInfo> pageInfo = new PageInfo<>(serviceList);

        // 正确返回 total 和当前页数据
        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

}
