package com.tpp.threat_perception_platform.service.impl;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.*;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.HostService;
import com.tpp.threat_perception_platform.service.RabbitService;
import com.tpp.threat_perception_platform.service.WeakpasswordRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


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
    public ResponseResult hotfixDiscovery(HotfixParam param) {
        // 验证Mac地址

        if (!isOnline(param.getMacAddress())){
            return new ResponseResult<>(1003, "主机不在线！");
        }
        // 初始化探测类型
        param.setType("hotfix");
        // 将param转换成JSON
        String json = JSON.toJSONString(param);
        // 组装队列的名字
        String routingKey=param.getMacAddress().replace(":","");
        rabbitService.sendMessage("agent_exchange",routingKey,json);
        return new ResponseResult(0, "补丁探测任务已下发，请稍后查看！");
    }

    @Override
    public ResponseResult weakpasswordDiscovery(WeakpasswordParam param){
        if (!isOnline(param.getMacAddress())){
            return new ResponseResult<>(1003, "主机不在线！");
        }

        param.setType("password");
        String ip = hostMapper.getIpByMac(param.getMacAddress());
        param.setIpAddress(param.getMacAddress());
        // 将param转换成JSON
        String json = JSON.toJSONString(param);
        // 组装队列的名字
        String routingKey=param.getMacAddress().replace(":","");
        rabbitService.sendMessage("agent_exchange",routingKey,json);
        return new ResponseResult(0, "弱密码探测任务已下发，请稍后查看！");
    }

    @Override
    public ResponseResult vulnerabilityDiscovery(VulnerabilityParam param){
        if (!isOnline(param.getMacAddress())){
            return new ResponseResult<>(1003, "主机不在线！");
        }

        param.setType("vulnerability");
        String ip = hostMapper.getIpByMac(param.getMacAddress());
        param.setIpAddress(param.getMacAddress());
        // 将param转换成JSON
        String json = JSON.toJSONString(param);
        // 组装队列的名字
        String routingKey=param.getMacAddress().replace(":","");
        rabbitService.sendMessage("agent_exchange",routingKey,json);
        return new ResponseResult(0, "漏洞探测任务已下发，请稍后查看！");
    }

    /**
     * 判断主机是否在线
     */
    private Boolean isOnline(String macAddress) {
        // 通过mac地址查询主机信息
        Host dbHost = hostMapper.selectByMacAddress(macAddress);
        // 去比较更新时间和当前时间判断主机是否在线
        if (dbHost == null || dbHost.getUpdateTime() == null|| new Date().getTime() - dbHost.getUpdateTime().getTime()>4000) {
            return false;
        }
        return true;
    }

}
