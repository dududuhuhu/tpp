package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AppInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.sql.Timestamp;

public interface AppInfoService {

    public ResponseResult appList(MyParam param);

    public ResponseResult saveApp(AppInfo appInfo, Timestamp now);

    public ResponseResult editApp(AppInfo appInfo);

    // public ResponseResult deleteApp(Integer[] ids);
}