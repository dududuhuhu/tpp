package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.LoginAction;
import com.tpp.threat_perception_platform.pojo.LoginLog;

import java.util.Date;
import java.util.List;

/**
* @author 34617
* @description 针对表【login_action(主机登录行为记录表)】的数据库操作Mapper
* @createDate 2025-06-18 09:41:01
* @Entity com.tpp.threat_perception_platform.pojo.LoginAction
*/
public interface LoginActionMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LoginAction record);

    int insertSelective(LoginAction record);

    LoginAction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginAction record);

    int updateByPrimaryKey(LoginAction record);

    LoginLog selectByloginLogIdAndeventIdAndtimestamp(Integer loginLogId, Integer eventId, Date timestamp);

    List<LoginAction> selectByLoginLogId(Integer id);
}
