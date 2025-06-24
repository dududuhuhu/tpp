package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.SystemRiskAiReport;

/**
* @author 25572
* @description 针对表【system_risk_ai_report】的数据库操作Mapper
* @createDate 2025-06-24 14:07:48
* @Entity com.tpp.threat_perception_platform.pojo.SystemRiskAiReport
*/
public interface SystemRiskAiReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SystemRiskAiReport record);

    int insertSelective(SystemRiskAiReport record);

    SystemRiskAiReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemRiskAiReport record);

    int updateByPrimaryKey(SystemRiskAiReport record);

    SystemRiskAiReport selectByMac(String mac);

    int insertOrUpdate(SystemRiskAiReport record);



}
