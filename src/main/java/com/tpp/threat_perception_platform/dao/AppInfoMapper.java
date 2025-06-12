package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.AppInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
* @author dawn
* @description 针对表【app_info】的数据库操作Mapper
* @createDate 2025-06-10 21:55:46
* @Entity com.tpp.threat_perception_platform.pojo.AppInfo
*/
public interface AppInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AppInfo record);

    int insertSelective(AppInfo record);

    AppInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AppInfo record);

    int updateByPrimaryKey(AppInfo record);

    List<AppInfo> findAll(@Param("mac") String mac);

    AppInfo selectByMacAndDisplayName(String mac, String displayName);


}
