package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author lup
* @description 针对表【role】的数据库操作Mapper
* @createDate 2025-06-04 19:23:43
* @Entity com.tpp.threat_perception_platform.pojo.Role
*/
public interface RoleMapper {

    int deleteByPrimaryKey(Long roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    Role selectByRoleName(String roleName);

    List<Role> selectAll(@Param("param") MyParam param);

}
