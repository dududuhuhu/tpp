package com.tpp.thread_preception_platform.mapper;

import com.tpp.thread_preception_platform.pojo.Role;

/**
* @author lup
* @description 针对表【role】的数据库操作Mapper
* @createDate 2025-06-04 16:54:19
* @Entity com.tpp.thread_preception_platform.pojo.Role
*/
public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

}
