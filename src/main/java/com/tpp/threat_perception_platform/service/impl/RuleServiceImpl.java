package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.*;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    @Autowired
    private WinCveDbMapper winCveDbMapper;

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

    @Override
    public ResponseResult applicationDelete(Integer[] ids) {
        applicationRiskRulesMapper.delete(ids);
        return new ResponseResult<>(0, "删除成功！");
    }

    @Override
    public ResponseResult applicationSave(ApplicationRiskRules applicationRiskRules) {
        // 先查询 是否有用户
        ApplicationRiskRules db_rule = applicationRiskRulesMapper.selectByRuleName(applicationRiskRules.getRiskName());
        if ( db_rule!= null){
            return new ResponseResult<>(1003, "规则已存在！");
        }
        applicationRiskRules.setStatus("active");
        applicationRiskRules.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        // 添加
        applicationRiskRulesMapper.insertSelective(applicationRiskRules);
        return new ResponseResult<>(0, "添加成功！");
    }

    @Override
    public ResponseResult applicatiionRiskRulesList(MyParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<ApplicationRiskRules> applicationList = null;
        if (param.getSearchType() != null && param.getSearchType() != "" && param.getKeywords() != null && !param.getKeywords().isEmpty()) {
            applicationList = applicationRiskRulesMapper.findRulesBySearchTypeAndKeywords(param);
        } else {
            applicationList = applicationRiskRulesMapper.findAll();
        }
        // 构架pageInfo
        PageInfo<ApplicationRiskRules> pageInfo = new PageInfo<>(applicationList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult hotfixRulesList(MyParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<WinCveDb> hotfixRuleList = null;
        if (param.getSearchType() != null && param.getSearchType() != "" && param.getKeywords() != null && !param.getKeywords().isEmpty()) {
            hotfixRuleList = winCveDbMapper.findRulesBySearchTypeAndKeywords(param);
        } else {
            hotfixRuleList = winCveDbMapper.findAll();
        }
        // 构架pageInfo
        PageInfo<WinCveDb> pageInfo = new PageInfo<>(hotfixRuleList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult hotfixDelete(Integer[] ids) {
        winCveDbMapper.delete(ids);
        return new ResponseResult<>(0, "删除成功！");
    }

    @Override
    public ResponseResult hotfixSave(WinCveDb winCveDb) {
        // 先查询 是否有用户
        WinCveDb db_rule = winCveDbMapper.selectByCve(winCveDb.getCve());
        if ( db_rule!= null){
            winCveDbMapper.updateKbListByCve(winCveDb);
            return new ResponseResult<>(0, "更新成功！");
        }
        winCveDb.setDt(String.valueOf(new Timestamp(System.currentTimeMillis())));
        // 添加
        winCveDbMapper.insertSelective(winCveDb);
        return new ResponseResult<>(0, "添加成功！");
    }

    @Override
    public ResponseResult vulnerabilityRulesList(MyParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<VulnerabilityRules> vulnerabilityList = null;
        if (param.getSearchType() != null && param.getSearchType() != "" && param.getKeywords() != null && !param.getKeywords().isEmpty()) {
            vulnerabilityList = vulnerabilityRulesMapper.findRulesBySearchTypeAndKeywords(param);
        } else {
            vulnerabilityList = vulnerabilityRulesMapper.findAll();
        }
        // 构架pageInfo
        PageInfo<VulnerabilityRules> pageInfo = new PageInfo<>(vulnerabilityList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
