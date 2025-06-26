package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.InTime;

import java.util.Date;
import java.util.List;

/**
* @author 34617
* @description 针对表【in_time】的数据库操作Mapper
* @createDate 2025-06-26 09:38:08
* @Entity com.tpp.threat_perception_platform.pojo.InTime
*/
public interface InTimeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(InTime record);

    int insertSelective(InTime record);

    InTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InTime record);

    int updateByPrimaryKey(InTime record);

    InTime selectByMacAndEventIdAndEventTime(String mac, String eventId, Date eventTime);

    List<InTime> findAll();
}
