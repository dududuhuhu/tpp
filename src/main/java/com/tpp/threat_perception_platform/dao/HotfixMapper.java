package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.Hotfix;

import java.util.List;

/**
* @author 34617
* @description 针对表【hotfix(补丁表：记录设备与补丁的对应关系)】的数据库操作Mapper
* @createDate 2025-06-14 21:26:54
* @Entity com.tpp.threat_perception_platform.pojo.Hotfix
*/
public interface HotfixMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Hotfix record);

    int insertSelective(Hotfix record);

    Hotfix selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Hotfix record);

    int updateByPrimaryKey(Hotfix record);

    List<Hotfix> findAll();

    Hotfix selectByMacAndHotfixId(String mac, String hotfixId);

    List<String> getHotfixIdsByMac(String mac);
}
