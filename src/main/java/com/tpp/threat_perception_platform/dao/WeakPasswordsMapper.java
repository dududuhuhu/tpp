package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.WeakPasswords;

import java.util.List;

/**
* @author dawn
* @description 针对表【weak_passwords】的数据库操作Mapper
* @createDate 2025-06-14 22:35:28
* @Entity com.tpp.threat_perception_platform.pojo.WeakPasswords
*/
public interface WeakPasswordsMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WeakPasswords record);

    int insertSelective(WeakPasswords record);

    WeakPasswords selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeakPasswords record);

    int updateByPrimaryKey(WeakPasswords record);

    List<String> selectAllWeakPasswords();

    List<WeakPasswords> findAll();
}
