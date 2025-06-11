package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.ProcessInfo;

import java.util.List;

/**
* @author dawn
* @description 针对表【process_info】的数据库操作Mapper
* @createDate 2025-06-10 23:39:20
* @Entity com.tpp.threat_perception_platform.pojo.ProcessInfo
*/
public interface ProcessInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProcessInfo record);

    int insertSelective(ProcessInfo record);

    ProcessInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProcessInfo record);

    int updateByPrimaryKey(ProcessInfo record);

    List<ProcessInfo> selectByMac(String mac);

    ProcessInfo selectByMacAndPid(String mac, Integer pid);

}
