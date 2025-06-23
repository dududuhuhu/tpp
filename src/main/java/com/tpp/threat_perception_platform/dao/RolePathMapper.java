package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.RolePath;

/**
* @author dawn
* @description 针对表【role_path】的数据库操作Mapper
* @createDate 2025-06-23 09:32:50
* @Entity com.tpp.threat_perception_platform.pojo.RolePath
*/
public interface RolePathMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RolePath record);

    int insertSelective(RolePath record);

    RolePath selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePath record);

    int updateByPrimaryKey(RolePath record);

}
