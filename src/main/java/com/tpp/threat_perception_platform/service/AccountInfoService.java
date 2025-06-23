package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.Date;

public interface AccountInfoService {
    public int analyzeAndSaveAccountInfo(AccountInfo accountInfo, Date now);

    public ResponseResult accountList(MyParam param);




}
