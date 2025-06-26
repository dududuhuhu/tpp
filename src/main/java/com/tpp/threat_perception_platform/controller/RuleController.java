package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.dao.WeakPasswordsMapper;
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
    @Autowired
    private WeakPasswordsMapper weakPasswordsMapper;

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




    @PostMapping("/rule/hotfix/list")
    public ResponseResult hotfixRulesList(MyParam param){
        return ruleService.hotfixRulesList(param);
    }

    @PostMapping("/rule/hotfix/delete")
    public ResponseResult hotfixRulesDelete(@RequestParam("ids[]") Integer[] ids){
        return ruleService.hotfixDelete(ids);
    }
    @PostMapping("/rule/hotfix/save")
    public ResponseResult hotfixRulesSave(@RequestBody WinCveDb winCveDb){
        return ruleService.hotfixSave(winCveDb);
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
    @PostMapping("/rule/weakPassword/save")
    public ResponseResult weakPasswordSave(@RequestBody WeakPasswords weakPasswords){
        return ruleService.weakPasswordSave(weakPasswords);
    }
    @PostMapping("/rule/weakPassword/delete")
    public ResponseResult weakPasswordDelete(@RequestParam("ids[]") Integer[] ids){
        return ruleService.weakPasswordDelete(ids);
    }


    @PostMapping("/rule/vulnerability/list")
    public ResponseResult vulnerabilityRulesList( MyParam param){
        System.out.println("controller_param:"+param);
        return ruleService.vulnerabilityRulesList(param);
    }
    @PostMapping("/rule/vulnerability/save")
    public ResponseResult vulRulesSave(@RequestBody VulnerabilityRules vulnerabilityRules){
        return ruleService.vulRulesSave(vulnerabilityRules);
    }
    @PostMapping("/rule/vulnerability/delete")
    public ResponseResult vulRulesDelete(@RequestParam("ids[]") Integer[] ids){
        return ruleService.vulRulesDelete(ids);
    }



    @PostMapping("/rule/system/save")
    public ResponseResult systemRulesSave(@RequestBody SystemRiskRules systemRiskRules){
        return ruleService.systemSave(systemRiskRules);
    }
    @PostMapping("/rule/system/list")
    public ResponseResult systemRulesList(MyParam param){
        return ruleService.systemRiskRulesList(param);
    }
    @PostMapping("/rule/system/delete")
    public ResponseResult systemRulesDelete(@RequestParam("ids[]") Integer[] ids){
        return ruleService.systemRulesDelete(ids);
    }







}
