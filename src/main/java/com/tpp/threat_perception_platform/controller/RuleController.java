package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/rule/vulnerability")
    public List<VulnerabilityRules>  getAllVulnerabilityRules() {
        return ruleService.getAllVulnerabilityRules();
    }

    @PostMapping("/rule/application/list")
    public ResponseResult applicationRiskRulesList(MyParam param){
        return ruleService.applicatiionRiskRulesList(param);
    }

    @PostMapping("/rule/application/delete")
    public ResponseResult applicationRulesDelete(@RequestParam("ids[]") Integer[] ids){
        return ruleService.applicationDelete(ids);
    }
    @PostMapping("/rule/application/save")
    public ResponseResult applicationRulesSave(@RequestBody ApplicationRiskRules applicationRiskRules){
        return ruleService.applicationSave(applicationRiskRules);
    }

    @PostMapping("/rule/weakPassword/list")
    public ResponseResult weakPassword(MyParam param){
        return ruleService.weakPasswordList(param);
    }

}
