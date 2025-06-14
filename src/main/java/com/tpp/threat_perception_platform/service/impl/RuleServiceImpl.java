package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.dao.ApplicationRiskRulesMapper;
import com.tpp.threat_perception_platform.dao.SystemRiskRulesMapper;
import com.tpp.threat_perception_platform.dao.VulnerabilityRulesMapper;
import com.tpp.threat_perception_platform.dao.WeakPasswordsMapper;
import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;
import com.tpp.threat_perception_platform.pojo.SystemRiskRules;
import com.tpp.threat_perception_platform.pojo.VulnerabilityRules;
import com.tpp.threat_perception_platform.pojo.WeakPasswords;
import com.tpp.threat_perception_platform.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private ApplicationRiskRulesMapper applicationRiskRulesMapper;

    @Autowired
    private SystemRiskRulesMapper systemRiskRulesMapper;
    @Autowired
    private WeakPasswordsMapper weakPasswordsMapper;
    @Autowired
    private VulnerabilityRulesMapper vulnerabilityRulesMapper;

    @Override
    public List<ApplicationRiskRules> getAllApplicationRiskRules() {
        return applicationRiskRulesMapper.selectAllRules();
    }

    @Override
    public List<SystemRiskRules> getAllSystemRiskRules() {
        return systemRiskRulesMapper.selectAllRules();
    }

    @Override
    public List<String>  getAllWeakPasswords() {
        return weakPasswordsMapper.selectAllWeakPasswords();
    }

    @Override
    public List<VulnerabilityRules> getAllVulnerabilityRules() {
        return vulnerabilityRulesMapper.selectALLVulnerabilityRules();
    }
}
