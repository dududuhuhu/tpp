package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.WeakpasswordRisk;

/**
* @author 34617
* @description 针对表【weakpassword_risk(弱密码风险用户表)】的数据库操作Mapper
* @createDate 2025-06-14 23:48:02
* @Entity com.tpp.threat_perception_platform.pojo.WeakpasswordRisk
*/
public interface WeakpasswordRiskMapper {

    int deleteByPrimaryKey(Long id);

    int insert(WeakpasswordRisk record);

    int insertSelective(WeakpasswordRisk record);

    WeakpasswordRisk selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeakpasswordRisk record);

    int updateByPrimaryKey(WeakpasswordRisk record);

}
