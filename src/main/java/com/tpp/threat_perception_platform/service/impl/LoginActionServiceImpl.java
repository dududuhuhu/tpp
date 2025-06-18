package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.dao.LoginActionMapper;
import com.tpp.threat_perception_platform.dao.LoginLogMapper;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.LoginAction;
import com.tpp.threat_perception_platform.pojo.LoginLog;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.LoginActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginActionServiceImpl implements LoginActionService {

    @Autowired
    private LoginActionMapper loginActionMapper;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public ResponseResult saveLoginAction(LoginAction loginAction) {
        // 先查询是否已存在
        // 可加去重逻辑，比如根据 loginLogId + eventId + timestamp 判重
        LoginLog db = loginActionMapper.selectByloginLogIdAndeventIdAndtimestamp(loginAction.getLoginLogId(),loginAction.getEventId(),loginAction.getTimestamp());
        if (db != null) {
            return new ResponseResult<>(1003, "该记录已存在！");
        }

        // 添加
        loginActionMapper.insert(loginAction);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 展示所有用户的所有action
     * @return
     */
    @Override
    public List<LogParam> getLoginLogsWithActions(){
        List<LoginLog> loginLogs = loginLogMapper.findAll();

        List<LogParam> result = new ArrayList<>();
        for (LoginLog log : loginLogs) {
            List<LoginAction> actions = loginActionMapper.selectByLoginLogId(log.getId());

            LogParam param = new LogParam();
            param.setMac(log.getMac());
            param.setUsername(log.getUsername());
            param.setLoginTime(log.getLoginTime());
            param.setLogoffTime(log.getLogoffTime());
            param.setIsRiskUser(log.getIsRiskUser());
            param.setIsRiskTime(log.getIsRiskTime());
            param.setActions(convertToActionParam(actions)); // 你可能要手动转换 LoginAction → Action

            result.add(param);
        }

        return result;
    }

    private List<LogParam.Action> convertToActionParam(List<LoginAction> actions) {
        List<LogParam.Action> result = new ArrayList<>();
        for (LoginAction act : actions) {
            LogParam.Action action = new LogParam.Action();
            action.setEventId(act.getEventId());
            action.setTimestamp(act.getTimestamp());
            action.setAction(act.getAction());
            action.setDetails(act.getDetails());
            result.add(action);
        }
        return result;
    }
}
