package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.RolePath;

/**
* @author lup
* @description 针对表【role_path】的数据库操作Mapper
* @createDate 2025-06-18 15:13:25
* @Entity com.tpp.threat_perception_platform.pojo.Role.RolePath
*/
public interface RolePathMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RolePath record);

    int insertSelective(RolePath record);

    RolePath selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePath record);

    int updateByPrimaryKey(RolePath record);

}
