package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;

import java.util.List;

/**
* @author dawn
* @description 针对表【application_risk_rules(应用风险检测规则表)】的数据库操作Mapper
* @createDate 2025-06-14 16:28:59
* @Entity com.tpp.threat_perception_platform.pojo.ApplicationRiskRules
*/
public interface ApplicationRiskRulesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ApplicationRiskRules record);

    int insertSelective(ApplicationRiskRules record);

    ApplicationRiskRules selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplicationRiskRules record);

    int updateByPrimaryKey(ApplicationRiskRules record);

    List<ApplicationRiskRules> selectAllRules();
}
