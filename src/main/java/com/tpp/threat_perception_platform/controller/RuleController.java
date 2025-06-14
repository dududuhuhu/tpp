package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;
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


}
