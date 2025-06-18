package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.AcctChgLog;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

/**
* @author 25572
* @description 针对表【acct_chg_log(账号变更日志表)】的数据库操作Mapper
* @createDate 2025-06-18 10:27:59
* @Entity com.tpp.threat_perception_platform.pojo.AcctChgLog
*/
public interface AcctChgLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AcctChgLog record);

    int insertSelective(AcctChgLog record);

    AcctChgLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AcctChgLog record);

    int updateByPrimaryKey(AcctChgLog record);

    // 根据mac查询
    List<AcctChgLog> selectByMac(String mac);

    // 根据参数查询
    List<AcctChgLog> selectByParam(LogParam param);

    // 根据一组 ID 批量查询日志
    List<AcctChgLog> selectByIds(List<Long> ids);


    AcctChgLog selectByUniqueFields(@Param("mac") String mac, @Param("action") LogParam.Action action);

    // AcctChgLogMapper.java
    List<AcctChgLog> selectByMacOrderedByTime(@Param("mac") String mac);




}
