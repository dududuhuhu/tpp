package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dawn
* @description 针对表【host(主机表)】的数据库操作Mapper
* @createDate 2025-06-06 11:07:36
* @Entity com.tpp.threat_perception_platform.pojo.Host
*/
public interface HostMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Host record);

    int insertSelective(Host record);

    Host selectByPrimaryKey(Integer id);


    /**
     * 根据mac地址查询主机
     * @param macAddress
     * @return
     */
    Host selectByMacAddress(String macAddress);
    int updateByPrimaryKeySelective(Host record);

    int updateByPrimaryKey(Host record);

    /**
     * 根据mac地址更新主机信息
     * @param record
     * @return
     */
    int updateByMacAddress(Host record);
    List<Host> findAll();

    void deleteHost(Integer[] ids);

    List<Host> findHostsBySearchTypeAndKeywords(@Param("param") MyParam param );

    String getIpByMac(String macAddress);

}
