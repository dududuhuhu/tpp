package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
* @author dawn
* @description 针对表【role】的数据库操作Mapper
* @createDate 2025-06-04 16:17:49
* @Entity com.tpp.threat_perception_platform.pojo.Role
*/

public interface RoleMapper {

    int deleteByPrimaryKey(Long id);

    /**
     * 删除数据
     * @param ids
     */
    void deleteRole(@Param("ids") Integer[] ids);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    /**
     * 查询所有
     * @param param
     * @return
     */
    List<Role> findAll(@Param("param") MyParam param);

    Role selectByRoleName(@Param("roleName") String roleName);

}
