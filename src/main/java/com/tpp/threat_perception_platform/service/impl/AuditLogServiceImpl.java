package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.LoginActionMapper;
import com.tpp.threat_perception_platform.dao.LoginLogMapper;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.LoginAction;
import com.tpp.threat_perception_platform.pojo.LoginLog;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AuditLogService;
import com.tpp.threat_perception_platform.service.RabbitService;
import com.tpp.threat_perception_platform.vo.LoginLogWithActionsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private LoginActionMapper loginActionMapper;
    @Autowired
    private HostMapper hostMapper;
    @Autowired
    private RabbitService rabbitService;

    @Override
    public PageInfo<LoginLogWithActionsVO> getAuditLogList(LogParam param) {
        // 开启分页
        PageHelper.startPage(param.getPage(), param.getLimit());

        // 先分页查 LoginLog 列表
        List<LoginLog> logList = loginLogMapper.selectByCondition(param);

        // 手动封装成 VO
        List<LoginLogWithActionsVO> voList = new ArrayList<>();
        for (LoginLog log : logList) {
            LoginLogWithActionsVO vo = new LoginLogWithActionsVO();
            vo.setMac(log.getMac());
            vo.setUsername(log.getUsername());
            vo.setLoginTime(log.getLoginTime());
            vo.setLogoffTime(log.getLogoffTime());
            vo.setCreateTime(log.getCreateTime());

            // 查对应行为列表
            List<LoginAction> actions = loginActionMapper.selectByLoginLogId(log.getId());
            List<LoginLogWithActionsVO.AuditActionVO> actionVOList = new ArrayList<>();
            for (LoginAction act : actions) {
                LoginLogWithActionsVO.AuditActionVO actVO = new LoginLogWithActionsVO.AuditActionVO();
                actVO.setEventId(act.getEventId());
                actVO.setTimestamp(act.getTimestamp());
                actVO.setAction(act.getAction());
                actVO.setDetails(act.getDetails());
                actionVOList.add(actVO);
            }
            vo.setActions(actionVOList);

            voList.add(vo);
        }

        // 返回 PageInfo，保持分页特性
        PageInfo pageInfo = new PageInfo<>(logList);
        pageInfo.setList(voList); // 替换原始 list 为 VO list

        return pageInfo;
    }

    @Override
    public ResponseResult auditLogDiscovery() {
        String type="auditLog";
        List<Host> db_hostList = hostMapper.findAll();
        if(db_hostList.isEmpty()){
            return new ResponseResult<>(1003,"无主机在线！");
        }
        for(Host host : db_hostList){
            // 去比较更新时间和当前时间判断主机是否在线
            if (host != null && host.getUpdateTime() != null && new Date().getTime() - host.getUpdateTime().getTime() < 4000)
            {
                Map<String, String> map = new HashMap<>();
                map.put("type", "auditLog");
                String json = JSON.toJSONString(map);  // 结果是 {"type":"auditLog"}
                // 组装队列的名字
                String routingKey=host.getMacAddress().replace(":","");
                rabbitService.sendMessage("agent_exchange",routingKey,json);
            }
        }
        return new ResponseResult(0, "开始同步，请稍后查看！");
    }
}
