package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.param.SystemRiskParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRisk;
import com.tpp.threat_perception_platform.pojo.SystemRisk;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface SystemRiskService {
    ResponseResult systemRiskList(SystemRiskParam param);

    ResponseResult saveSystemRisk(SystemRisk systemRisk);

    ResponseResult editSystemRisk(SystemRisk systemRisk);

    ResponseResult getSystemRiskDetail(Integer id);


    /**
     * 查询此次探测发现的应用风险总数
     * @param param 探测参数（比如可传探测时间范围或主机）
     * @return 风险总数
     */
    ResponseResult getRiskCount(SystemRiskParam param);

    /**
     * 根据mac分析应用风险记录并生成AI报告
     * @param mac 主机MAC地址
     * @return 响应结果，包含报告内容
     */
    ResponseResult analyzeAndSaveSystemRiskReport(String mac);


}
