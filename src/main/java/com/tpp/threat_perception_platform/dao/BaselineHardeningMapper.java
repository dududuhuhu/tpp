package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.BaselineHardening;

import java.util.List;

/**
* @author 34617
* @description 针对表【baseline_hardening(基线加固记录表)】的数据库操作Mapper
* @createDate 2025-06-23 11:14:46
* @Entity com.tpp.threat_perception_platform.pojo.BaselineHardening
*/
public interface BaselineHardeningMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BaselineHardening record);

    int insertSelective(BaselineHardening record);

    BaselineHardening selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaselineHardening record);

    int updateByPrimaryKey(BaselineHardening record);

    BaselineHardening selectByMacAndName(String mac, String name);

    List<BaselineHardening> findAll();

    BaselineHardening selectByTaskIDAndName(Integer taskId, String name);
}
