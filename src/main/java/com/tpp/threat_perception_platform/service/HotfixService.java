package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.HotfixParam;
import com.tpp.threat_perception_platform.pojo.Hotfix;
import com.tpp.threat_perception_platform.response.DangerousHotfix;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.sql.Timestamp;
import java.util.List;

public interface HotfixService {

    // public ResponseResult hotfixList(HotfixParam param);

    public ResponseResult saveHotfix(Hotfix hotfix, Timestamp now);

    // public ResponseResult editApp(Hotfix hotfix);

    // 添加重载方法（无分页）
//    ResponseResult<List<DangerousHotfix>> getDangerousPatches(String mac);

    public ResponseResult<List<DangerousHotfix>> getDangerousPatch(HotfixParam param);


    ResponseResult analyzeAndSaveHotfixRiskReport(List<DangerousHotfix> hotfixes, String mac);
}
