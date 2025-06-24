package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.ApplicationRiskAiReport;

/**
* @author 25572
* @description 针对表【application_risk_ai_report】的数据库操作Mapper
* @createDate 2025-06-24 10:00:43
* @Entity com.tpp.threat_perception_platform.pojo.ApplicationRiskAiReport
*/
public interface ApplicationRiskAiReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ApplicationRiskAiReport record);

    int insertSelective(ApplicationRiskAiReport record);

    ApplicationRiskAiReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplicationRiskAiReport record);

    int updateByPrimaryKey(ApplicationRiskAiReport record);

    /**
     * 根据 MAC 查询 AI 分析报告
     * @param mac 主机 MAC 地址
     * @return AI 分析报告实体
     */
    ApplicationRiskAiReport selectByMac(String mac);

    /**
     * 插入或更新 AI 报告（根据 mac 唯一判断）
     * @param record AI 报告对象
     * @return 受影响行数
     */
    int insertOrUpdate(ApplicationRiskAiReport record);



}
