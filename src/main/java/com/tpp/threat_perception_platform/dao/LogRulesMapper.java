package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.LogRules;

import java.util.List;

/**
* @author dawn
* @description 针对表【log_rules】的数据库操作Mapper
* @createDate 2025-06-24 15:26:59
* @Entity com.tpp.threat_perception_platform.pojo.LogRules
*/
public interface LogRulesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LogRules record);

    int insertSelective(LogRules record);

    LogRules selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogRules record);

    int updateByPrimaryKey(LogRules record);

    List<LogRules> selectByPlatform(String platform);

}
