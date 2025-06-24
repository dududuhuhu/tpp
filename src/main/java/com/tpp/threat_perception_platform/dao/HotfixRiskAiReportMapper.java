package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.HotfixRiskAiReport;

/**
* @author 34617
* @description 针对表【hotfix_risk_ai_report】的数据库操作Mapper
* @createDate 2025-06-24 11:42:59
* @Entity com.tpp.threat_perception_platform.pojo.HotfixRiskAiReport
*/
public interface HotfixRiskAiReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(HotfixRiskAiReport record);

    int insertSelective(HotfixRiskAiReport record);

    HotfixRiskAiReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HotfixRiskAiReport record);

    int updateByPrimaryKey(HotfixRiskAiReport record);

    HotfixRiskAiReport selectByMac(String mac);

    void insertOrUpdate(HotfixRiskAiReport report);
}
