package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.BaselineDetect;
import com.tpp.threat_perception_platform.pojo.BaselineDetectAiReport;
import com.tpp.threat_perception_platform.pojo.SystemRiskAiReport;

import java.util.List;

/**
* @author 25572
* @description 针对表【baseline_detect_ai_report】的数据库操作Mapper
* @createDate 2025-06-25 16:26:19
* @Entity com.tpp.threat_perception_platform.pojo.BaselineDetectAiReport
*/
public interface BaselineDetectAiReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BaselineDetectAiReport record);

    int insertSelective(BaselineDetectAiReport record);

    BaselineDetectAiReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaselineDetectAiReport record);

    int updateByPrimaryKey(BaselineDetectAiReport record);

    BaselineDetectAiReport selectByMac(String mac);

    int insertOrUpdate(BaselineDetectAiReport record);


}
