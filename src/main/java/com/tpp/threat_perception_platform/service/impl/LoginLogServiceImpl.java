package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.AccountInfoMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.LoginLogMapper;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.LoginLog;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.LoginLogService;
import com.tpp.threat_perception_platform.service.RabbitService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private HostMapper hostMapper;
    @Autowired
    private AccountInfoMapper accountInfoMapper;
    @Autowired
    private RabbitService rabbitService;

    /**
     * 保存
     * @param loginLog
     * @return
     */
    @Override
    public ResponseResult saveLoginLog(LoginLog loginLog) {
        // 先查询是否已存在
        LoginLog db = loginLogMapper.selectByMacAndUsernameAndLoginTime(loginLog.getMac(), loginLog.getUsername(),loginLog.getLoginTime());
        if (db != null) {
            // 存在但更新
            loginLog.setId(db.getId());
            loginLogMapper.updateByMacAndUsernameAndLoginTime(loginLog);
            return new ResponseResult<>(1003, "该记录已存在！");
        }

        // 添加
        loginLogMapper.insert(loginLog);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 查询
     * @return
     */
    @Override
    public ResponseResult loginLogList(LogParam param){
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<LoginLog> loginLogList = loginLogMapper.findAll();
        // 构架pageInfo
        PageInfo<LoginLog> pageInfo = new PageInfo<>(loginLogList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult loginLogDiscovery() {
        String type="loginLog";
        List<Host> db_hostList = hostMapper.findAll();
        if(db_hostList.isEmpty()){
            return new ResponseResult<>(1003,"无主机在线！");
        }
        for(Host host : db_hostList){
            // 去比较更新时间和当前时间判断主机是否在线
            if (host != null && host.getUpdateTime() != null && new Date().getTime() - host.getUpdateTime().getTime() < 4000)
            {
                Map<String, Object> map = new HashMap<>();
                map.put("type", "loginLog");
                List<String> names = accountInfoMapper.selectAllNamesByMac(host.getMacAddress());
                map.put("username",names);
                String json = JSON.toJSONString(map);  // 结果是 {"type":"auditLog"}
                // 组装队列的名字
                String routingKey=host.getMacAddress().replace(":","");
                rabbitService.sendMessage("agent_exchange",routingKey,json);
            }
        }
        return new ResponseResult(0, "开始同步，请稍后查看！");
    }

}
