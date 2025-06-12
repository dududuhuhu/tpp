package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.ServiceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dawn
* @description 针对表【service_info】的数据库操作Mapper
* @createDate 2025-06-11 12:48:13
* @Entity com.tpp.threat_perception_platform.pojo.ServiceInfo
*/
public interface ServiceInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ServiceInfo record);

    int insertSelective(ServiceInfo record);

    ServiceInfo selectByPrimaryKey(Long id);

    List<ServiceInfo> selectByHostId(Long hostId);

    int updateByPrimaryKeySelective(ServiceInfo record);

    int updateByPrimaryKey(ServiceInfo record);

    int deleteByHostId(Long hostId);

    int insertBatch(@Param("list") List<ServiceInfo> list, @Param("hostId") Long hostId);

}
