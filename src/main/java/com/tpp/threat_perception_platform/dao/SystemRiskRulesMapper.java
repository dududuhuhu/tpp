package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;
import com.tpp.threat_perception_platform.pojo.SystemRiskRules;

import java.util.List;

/**
* @author dawn
* @description 针对表【system_risk_rules(系统风险检测规则表)】的数据库操作Mapper
* @createDate 2025-06-14 21:44:28
* @Entity com.tpp.threat_perception_platform.pojo.SystemRiskRules
*/
public interface SystemRiskRulesMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SystemRiskRules record);

    int insertSelective(SystemRiskRules record);

    SystemRiskRules selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemRiskRules record);

    int updateByPrimaryKey(SystemRiskRules record);

    List<SystemRiskRules> selectAllRules();

    SystemRiskRules selectByRuleName(String riskName);

    List<SystemRiskRules> findRulesBySearchTypeAndKeywords(MyParam param);

    List<SystemRiskRules> findAll();
}
