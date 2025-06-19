package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.AcctChgLogMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.AcctChgLog;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AcctChgLogService;
import com.tpp.threat_perception_platform.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AcctChgLogServiceImpl implements AcctChgLogService {

    @Autowired
    private AcctChgLogMapper acctChgLogMapper;
    @Autowired
    private HostMapper hostMapper;
    @Autowired
    private RabbitService rabbitService;

    /**
     * 保存账号变更日志数据
     */
    @Override
    public Integer saveAcctChgLog(String mac, List<LogParam.Action> actions) {
        if (actions == null || actions.isEmpty()) {
            return 0;
        }

        int count = 0;
        for (LogParam.Action action : actions) {
            AcctChgLog log = new AcctChgLog();
            log.setMac(mac);
            log.setEventId(action.getEventId());
            log.setEventTime(action.getTimestamp());
            log.setAction(action.getAction());
            log.setTargetUser(action.getTargetUser());
            log.setOperatorUser(action.getOperatorUser());

            // 查询是否已存在相同日志记录
            AcctChgLog existingLog = acctChgLogMapper.selectByUniqueFields(mac, action);



            Date now = new Date();
            if (existingLog != null) {
                // 已存在，更新 update_time
                existingLog.setUpdatedAt(now);
                count += acctChgLogMapper.updateByPrimaryKey(existingLog);
            } else {
                // 不存在，插入新记录
                log.setCreatedAt(now);
                log.setUpdatedAt(now);
                count += acctChgLogMapper.insert(log);
            }
        }

        return count;
    }



    /**
     * 按MAC查询账号变更日志
     */
    @Override
    public ResponseResult<List<AcctChgLog>> getAcctChgLogByMac(String mac) {
        List<AcctChgLog> logs = acctChgLogMapper.selectByMac(mac);
        long count = logs != null ? logs.size() : 0;
        return new ResponseResult<>(count, logs);
    }

    /**
     * 条件查询账号变更日志
     */
    @Override
    public ResponseResult<List<AcctChgLog>> queryAcctChgLog(LogParam param) {
        List<AcctChgLog> logs = acctChgLogMapper.selectByParam(param);
        long count = logs != null ? logs.size() : 0;
        return new ResponseResult<>(count, logs);
    }

    @Override
    public ResponseResult<List<AcctChgLog>> listAcctChgLog(LogParam param) {
        // 开启分页，假设param中有page和limit
        PageHelper.startPage(param.getPage(), param.getLimit());

        // 查询符合条件的日志列表
        List<AcctChgLog> logList = acctChgLogMapper.selectByParam(param);

        // 构造分页信息
        PageInfo<AcctChgLog> pageInfo = new PageInfo<>(logList);

        // 返回总数和当前页数据
        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult accountChangeLogDiscovery() {
        List<Host> db_hostList = hostMapper.findAll();
        if(db_hostList.isEmpty()){
            return new ResponseResult<>(1003,"无主机在线！");
        }
        for(Host host : db_hostList){
            // 去比较更新时间和当前时间判断主机是否在线
            if (host != null && host.getUpdateTime() != null && new Date().getTime() - host.getUpdateTime().getTime() < 4000)
            {
                Map<String, String> map = new HashMap<>();
                map.put("type", "accountChangeLog");
                String json = JSON.toJSONString(map);  // 结果是 {"type":"auditLog"}
                // 组装队列的名字
                String routingKey=host.getMacAddress().replace(":","");
                rabbitService.sendMessage("agent_exchange",routingKey,json);
            }
        }
        return new ResponseResult(0, "开始同步，请稍后查看！");
    }

}
