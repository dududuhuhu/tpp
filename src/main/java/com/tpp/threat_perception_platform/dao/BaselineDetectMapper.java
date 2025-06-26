package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.ApplicationRisk;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;

import java.util.List;

/**
* @author 34617
* @description 针对表【baseline_detect】的数据库操作Mapper
* @createDate 2025-06-23 10:14:25
* @Entity com.tpp.threat_perception_platform.pojo.BaselineDetect
*/
public interface BaselineDetectMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BaselineDetect record);

    int insertSelective(BaselineDetect record);

    BaselineDetect selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BaselineDetect record);

    int updateByPrimaryKey(BaselineDetect record);

    BaselineDetect selectByMacAndName(String mac, String name);

    List<BaselineDetect> findAll();

    /**
     * 根据 MAC 地址查询该主机的全部基线探测记录
     * @param mac 主机 MAC 地址
     * @return 风险记录列表
     */
    List<BaselineDetect> selectByMac(String mac);

}
