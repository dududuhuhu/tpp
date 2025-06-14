package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;
import com.tpp.threat_perception_platform.pojo.SystemRiskRules;
import com.tpp.threat_perception_platform.pojo.WeakPasswords;
import com.tpp.threat_perception_platform.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("/rule/applicationRisk")
    public List<ApplicationRiskRules> getAllApplicationRiskRules() {
        return ruleService.getAllApplicationRiskRules();
    }

    @GetMapping("/rule/systemRisk")
    public List<SystemRiskRules> getAllSystemRiskRules() {
        return ruleService.getAllSystemRiskRules();
    }

    @GetMapping("/rule/weakPassword")
    public List<String>  getAllWeakPasswords() {
        return ruleService.getAllWeakPasswords();
    }




}
