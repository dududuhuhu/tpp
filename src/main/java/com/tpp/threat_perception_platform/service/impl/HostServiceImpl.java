package com.tpp.threat_perception_platform.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.param.AssetsParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.SystemRiskParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostService;
import com.tpp.threat_perception_platform.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HostServiceImpl implements HostService {
    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private RabbitService rabbitService;

    @Override
    public ResponseResult hostList(MyParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<Host> hostList = null;
        if (param.getSearchType() != null && param.getSearchType() != "" && param.getKeywords() != null && !param.getKeywords().isEmpty()) {
             hostList = hostMapper.findHostsBySearchTypeAndKeywords(param);
        } else {
             hostList = hostMapper.findAll();
        }
        // 通过列表里面的update_time和当前时间进行对比
        // 如果当前时间-update_time>4s，则更新主机状态位0
        for (Host host : hostList) {
            Date updateTime = host.getUpdateTime();
            if(updateTime == null){
                host.setStatus(0);
                continue;
            }
            //通过当前-updateTime进行判断
            if(new Date().getTime() - updateTime.getTime() > 4000){
                host.setStatus(0);
            }
        }

//        System.out.println("param：" + param);
        // 构架pageInfo
        PageInfo<Host> pageInfo = new PageInfo<>(hostList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public int saveHost(Host host) {
        try {
            // 先判断是否有MAC
            if (host.getMacAddress() == null || host.getMacAddress().equals("")) {
                System.out.println("MAC地址为空");
                return 1;
            }
            Host dbHost = hostMapper.selectByMacAddress(host.getMacAddress());
            if (dbHost != null) {
                System.out.println("找到相同MAC地址的主机，执行更新操作");
                host.setId(dbHost.getId());
                host.setStatus(1);
                host.setUpdateTime(new Timestamp(System.currentTimeMillis()));
                int updateResult = hostMapper.updateByPrimaryKeySelective(host);
                System.out.println("更新结果：" + updateResult);
                return updateResult;
            }
            // 在数据库里面没有对应主机的情况才会去创建队列
            // 组装队列名称 agent_[mac_address]_queue,mac_address需要去掉冒号
            String queueName = "agent_" + host.getMacAddress().replace(":", "") + "_queue";
            String routingKey=host.getMacAddress().replace(":","");
            rabbitService.createAgent("agent_exchange",queueName,routingKey);

            host.setStatus(1);
            host.setCreateTime(new Timestamp(System.currentTimeMillis()));
            int insertResult = hostMapper.insertSelective(host);
            System.out.println("插入结果：" + insertResult);
            return insertResult;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发生异常：" + e.getMessage());
            return -1; // 假设 -1 表示异常
        }
    }
    @Override
    public ResponseResult deleteHost(Integer[] ids) {
        hostMapper.deleteHost(ids);
        return new ResponseResult<>(0, "删除成功！");
    }

    @Override
    public ResponseResult editHost(Host host) {
        hostMapper.updateByPrimaryKeySelective(host);
        return new ResponseResult<>(0, "修改成功！");
    }

    @Override
    public int updateHostByMacAddress(Host host) {
        host.setStatus(1);
        host.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return hostMapper.updateByMacAddress(host);
    }

    @Override
    public ResponseResult assetsDiscovery(AssetsParam param) {
        // 验证探测的类型
        if(!param.getAccount().equals(0)&&!param.getAccount().equals(1)){
            return new ResponseResult<>(1004, "参数有误！");
        }
        // 通过mac地址查询主机信息
        Host dbHost = hostMapper.selectByMacAddress(param.getMacAddress());
        // 去比较更新时间和当前时间判断主机是否在线
        if (dbHost == null || dbHost.getUpdateTime() == null|| new Date().getTime() - dbHost.getUpdateTime().getTime()>4000) {
            return new ResponseResult<>(1003, "主机不在线！");
        }
        // 初始化探测类型
        param.setType("assets");
        // 将param转换成JSON
        String json = JSON.toJSONString(param);
        // 组装队列的名字
        String routingKey=param.getMacAddress().replace(":","");
        rabbitService.sendMessage("agent_exchange",routingKey,json);
        return new ResponseResult(0, "资产探测任务已下发，请稍后查看！");
    }


    @Override
    public ResponseResult appRiskDiscovery(ApplicationRiskParam param) {
        // 检查 macAddress 是否存在
        if (param.getMacAddress() == null || param.getMacAddress().isEmpty()) {
            return new ResponseResult<>(1004, "MAC地址不能为空！");
        }

        // 查询主机状态，确认在线
        Host dbHost = hostMapper.selectByMacAddress(param.getMacAddress());
        if (dbHost == null || dbHost.getUpdateTime() == null ||
                new Date().getTime() - dbHost.getUpdateTime().getTime() > 4000) {
            return new ResponseResult<>(1003, "主机不在线！");
        }

        // **在这里给 param 赋 ipAddress**
        param.setIpAddress(dbHost.getIpAddress());


        // 根据 appRisk 值决定 type
        if (param.getAppRisk() != null && param.getAppRisk() == 1) {
            param.setType("applicationRisk");
        } else {
            // 如果 appRisk 不为 1，可以自定义其他任务类型或报错
            return new ResponseResult<>(1004, "未知的应用风险任务类型！");
        }

        // 打印最终消息参数
        System.out.println("即将发送的任务参数: " + JSON.toJSONString(param));



// 重新序列化为 JSON
        String json = JSON.toJSONString(param);
        String routingKey = param.getMacAddress().replace(":", "");
        rabbitService.sendMessage("agent_exchange", routingKey, json);

        return new ResponseResult<>(0, "应用风险发现任务已下发，请稍后查看！");
    }

    @Override
    public ResponseResult systemRiskDiscovery(SystemRiskParam param) {
        // 检查 macAddress 是否存在
        if (param.getMacAddress() == null || param.getMacAddress().isEmpty()) {
            return new ResponseResult<>(1004, "MAC地址不能为空！");
        }

        // 查询主机状态，确认在线
        Host dbHost = hostMapper.selectByMacAddress(param.getMacAddress());
        if (dbHost == null || dbHost.getUpdateTime() == null ||
                new Date().getTime() - dbHost.getUpdateTime().getTime() > 4000) {
            return new ResponseResult<>(1003, "主机不在线！");
        }

        // 根据 systemRisk 值决定 type
        if (param.getSystemRisk() != null && param.getSystemRisk() == 1) {
            param.setType("systemRisk");
        } else {
            // 如果 systemRisk 不为 1，可以自定义其他任务类型或报错
            return new ResponseResult<>(1004, "未知的应用风险任务类型！");
        }

        // 打印最终消息参数
        System.out.println("即将发送的任务参数: " + JSON.toJSONString(param));

        // 转成 JSON 发送 RabbitMQ
        String json = JSON.toJSONString(param);
        String routingKey = param.getMacAddress().replace(":", "");
        rabbitService.sendMessage("agent_exchange", routingKey, json);

        return new ResponseResult<>(0, "系统风险发现任务已下发，请稍后查看！");
    }


}
