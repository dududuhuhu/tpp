package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.HostView;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
* @author lup
* @description 针对表【host】的数据库操作Mapper
* @createDate 2025-06-06 12:57:42
* @Entity com.tpp.threat_perception_platform.pojo.Host
*/
public interface HostMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Host record);

    int insertSelective(Host record);

    Host selectByPrimaryKey(Long id);

    Host selectByMacAddress(@Param("macAddress") String macAddress);

    int updateByPrimaryKeySelective(Host record);

    int updateByPrimaryKey(Host record);

    int updateOrInsertByMacAddress(@Param("host") Host host, @Param("currentTime") String currentTime);

    List<HostView> findAll(@Param("param") MyParam param);

    int updateStatus(@Param("macAddress") String macAddress, @Param("currentTime") String time);
}
