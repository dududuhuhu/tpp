package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.AcctChgReport;

/**
* @author 25572
* @description 针对表【acct_chg_report(账号变更日志风险分析报告表，存储对每条日志事件的风险评估结果)】的数据库操作Mapper
* @createDate 2025-06-18 11:17:41
* @Entity com.tpp.threat_perception_platform.pojo.AcctChgReport
*/
public interface AcctChgReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AcctChgReport record);

    int insertSelective(AcctChgReport record);

    AcctChgReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcctChgReport record);

    int updateByPrimaryKey(AcctChgReport record);

}
