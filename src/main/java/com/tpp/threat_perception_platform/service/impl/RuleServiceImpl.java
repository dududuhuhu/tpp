package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.dao.ApplicationRiskRulesMapper;
import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;
import com.tpp.threat_perception_platform.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private ApplicationRiskRulesMapper applicationRiskRulesMapper;

    @Override
    public List<ApplicationRiskRules> getAllApplicationRiskRules() {
        return applicationRiskRulesMapper.selectAllRules();
    }
}
