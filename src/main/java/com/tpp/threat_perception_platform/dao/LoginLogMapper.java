package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.LoginLog;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.Date;
import java.util.List;

/**
* @author 34617
* @description 针对表【login_log(主机登录日志表)】的数据库操作Mapper
* @createDate 2025-06-18 10:04:17
* @Entity com.tpp.threat_perception_platform.pojo.LoginLog
*/
public interface LoginLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LoginLog record);

    int insertSelective(LoginLog record);

    LoginLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginLog record);

    int updateByPrimaryKey(LoginLog record);

    LoginLog selectByMacAndUsernameAndLoginTime(String mac, String username, Date loginTime);

    List<LoginLog> findAll(@Param("params") LogParam params);

    List<LoginLog> selectByCondition(LogParam param);

    void updateByMacAndUsernameAndLoginTime(LoginLog loginLog);
}
