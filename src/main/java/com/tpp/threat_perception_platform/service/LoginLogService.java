package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.LoginLog;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface LoginLogService {
    ResponseResult saveLoginLog(LoginLog loginLog);

    ResponseResult loginLogList(LogParam param);
}
