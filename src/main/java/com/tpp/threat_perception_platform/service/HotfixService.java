package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.HotfixParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.pojo.WinCveDb;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface HotfixService {

    // public ResponseResult hotfixList(HotfixParam param);

    public ResponseResult saveHotfix(Hotfix hotfix);

    // public ResponseResult editApp(Hotfix hotfix);

    // 添加重载方法（无分页）
    ResponseResult<List<DangerousHotfix>> getDangerousPatches(String mac);

    public ResponseResult<List<DangerousHotfix>> getDangerousPatch(String mac, Integer page, Integer limit);

}
