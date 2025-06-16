package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.WinCveDb;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
* @author 34617
* @description 针对表【win_cve_db(Windows漏洞与KB补丁关系库)】的数据库操作Mapper
* @createDate 2025-06-14 21:32:23
* @Entity com.tpp.threat_perception_platform.pojo.WinCveDb
*/
public interface WinCveDbMapper {

    List<WinCveDb> findRulesBySearchTypeAndKeywords(MyParam param);

    int deleteByPrimaryKey(Long id);

    int insert(WinCveDb record);

    int insertSelective(WinCveDb record);

    WinCveDb selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WinCveDb record);

    int updateByPrimaryKey(WinCveDb record);

    List<WinCveDb> findAll();

    List<WinCveDb> findByHotfixId(@Param("hotfixId") String hotfixId);

    WinCveDb selectByCve(String cve);

    void delete(Integer[] ids);

    void updateKbListByCve(WinCveDb winCveDb);
}
