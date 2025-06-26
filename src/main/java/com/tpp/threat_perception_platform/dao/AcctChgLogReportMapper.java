package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.AcctChgLogReport;

/**
* @author dawn
* @description 针对表【acct_chg_log_report(账号变更日志风险分析报告表)】的数据库操作Mapper
* @createDate 2025-06-26 01:52:13
* @Entity com.tpp.threat_perception_platform.pojo.AcctChgLogReport
*/
public interface AcctChgLogReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AcctChgLogReport record);

    int insertSelective(AcctChgLogReport record);

    AcctChgLogReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcctChgLogReport record);

    int updateByPrimaryKey(AcctChgLogReport record);

    AcctChgLogReport selectByMac(String mac);

    void updateById(AcctChgLogReport report);
}
