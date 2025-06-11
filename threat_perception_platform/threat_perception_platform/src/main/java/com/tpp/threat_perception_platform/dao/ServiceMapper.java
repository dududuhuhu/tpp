package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author lup
* @description 针对表【service】的数据库操作Mapper
* @createDate 2025-06-10 19:09:44
* @Entity com.tpp.threat_perception_platform.pojo.Service
*/
public interface ServiceMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Service record);

    int insertSelective(Service record);

    Service selectByPrimaryKey(Long id);

    List<Service> selectByHostId(Long hostId);

    int updateByPrimaryKeySelective(Service record);

    int updateByPrimaryKey(Service record);

    int deleteByHostId(Long hostId);

    int insertBatch(@Param("list") List<Service> list, @Param("hostId") Long hostId);

}
