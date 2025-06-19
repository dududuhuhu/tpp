package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface AccountInfoService {
    public int analyzeAndSaveAccountInfo(AccountInfo accountInfo);

    public ResponseResult accountList(MyParam param);




}
