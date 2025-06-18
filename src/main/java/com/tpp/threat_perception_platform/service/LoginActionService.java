package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.LoginAction;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface LoginActionService {
    ResponseResult saveLoginAction(LoginAction loginAction);

    List<LogParam> getLoginLogsWithActions();
}
