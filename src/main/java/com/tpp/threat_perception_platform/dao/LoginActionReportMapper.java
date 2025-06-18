package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.LoginActionReport;

/**
* @author 34617
* @description 针对表【login_action_report(AI用户行为分析结果表)】的数据库操作Mapper
* @createDate 2025-06-18 15:30:32
* @Entity com.tpp.threat_perception_platform.pojo.LoginActionReport
*/
public interface LoginActionReportMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LoginActionReport record);

    int insertSelective(LoginActionReport record);

    LoginActionReport selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoginActionReport record);

    int updateByPrimaryKey(LoginActionReport record);

}
