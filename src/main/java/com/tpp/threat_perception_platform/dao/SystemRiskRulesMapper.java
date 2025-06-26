package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.RuleParam;
import com.tpp.threat_perception_platform.pojo.SystemRiskRules;

import java.util.List;

/**
* @author 34617
* @description 针对表【system_risk_rules(系统风险检测规则表)】的数据库操作Mapper
* @createDate 2025-06-23 15:01:45
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

    void delete(Integer[] ids);

    List<RuleParam> getRulesByPlatform(String platform);
}
