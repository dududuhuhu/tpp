package com.tpp.threat_perception_platform.service;
import com.tpp.threat_perception_platform.dao.ApplicationRiskRulesMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.*;
import com.tpp.threat_perception_platform.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RuleService {

    public List<ApplicationRiskRules> getAllApplicationRiskRules();

    public List<SystemRiskRules> getAllSystemRiskRules();

    public List<String>  getAllWeakPasswords();

    public List<VulnerabilityRules> getAllVulnerabilityRules();

    public ResponseResult applicationDelete(Integer[] ids);

    public ResponseResult applicationSave(ApplicationRiskRules applicationRiskRules);

    public ResponseResult applicatiionRiskRulesList(MyParam param);

    public ResponseResult hotfixRulesList(MyParam param);

    public ResponseResult hotfixDelete(Integer[] ids);

    public ResponseResult hotfixSave(WinCveDb winCveDb);

    public ResponseResult vulnerabilityRulesList(MyParam param);

    public ResponseResult systemSave(SystemRiskRules systemRiskRules);
}
