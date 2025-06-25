package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dawn
* @description 针对表【baseline_task】的数据库操作Mapper
* @createDate 2025-06-25 11:22:37
* @Entity com.tpp.threat_perception_platform.pojo.BaselineTask
*/
public interface BaselineTaskMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BaselineTask record);

    int insertSelective(BaselineTask record);

    BaselineTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaselineTask record);

    int updateByPrimaryKey(BaselineTask record);

    List<BaselineTask> findAll(@Param("param") MyParam param);

    BaselineTask selectByName(String taskName);

    void delete(Integer[] ids);
}
