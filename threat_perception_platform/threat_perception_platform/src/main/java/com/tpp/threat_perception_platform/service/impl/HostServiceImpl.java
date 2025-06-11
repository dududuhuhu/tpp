package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.AssetParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.HostView;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostService;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.service.RabbitService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HostServiceImpl implements HostService {

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public int saveHost(Host host) {
        LocalDateTime time = LocalDateTime.now();
        Host dbHost = hostMapper.selectByMacAddress(host.getMacAddress());
        if (dbHost == null) {
            String routingKey = host.getMacAddress().replaceAll(":", "");
            String queue_name = "agent_" + routingKey + "_queue";
            rabbitService.createAgentQueue("agent_exchange", queue_name, routingKey);
        }
        return hostMapper.updateOrInsertByMacAddress(host, time.toString());
    }

    @Override
    public ResponseResult listHost(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<HostView> hostList = hostMapper.findAll(param);
        PageInfo<HostView> pageInfo = new PageInfo<>(hostList);
        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult deleteHost(Long[] ids) {
        for (var id : ids) {
            if (hostMapper.deleteByPrimaryKey(id) <= 0) {
                return new ResponseResult(400, "Fail to delete host, id = " + id);
            }
        }
        return new ResponseResult(0, "Success to delete host");
    }

    @Override
    public int updateStatus(String macAddress) {
        LocalDateTime time = LocalDateTime.now();
        return hostMapper.updateStatus(macAddress, time.toString());
    }

    @Override
    public ResponseResult assetsDiscovery(AssetParam param) {
        System.out.println(param);
        if (param.getMacAddress() == null || param.getMacAddress().isEmpty() || !param.getServiceType().equals("assetsDiscovery")) {
            return new ResponseResult(400, "Invalid param");
        }

        try{
            String routingKey = param.getMacAddress().replaceAll(":", "");
            rabbitService.sendMessage("agent_exchange", routingKey, param.toString());
            return new ResponseResult(0, "Command sent successfully, please wait for results.");
        } catch (Exception e) {
            return new ResponseResult(400, "Fail to send message to agent_exchange");
        }
    }
}
