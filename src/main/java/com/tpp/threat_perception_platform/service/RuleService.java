package com.tpp.threat_perception_platform.service;
import com.tpp.threat_perception_platform.dao.ApplicationRiskRulesMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;
import com.tpp.threat_perception_platform.pojo.SystemRiskRules;
import com.tpp.threat_perception_platform.pojo.VulnerabilityRules;
import com.tpp.threat_perception_platform.pojo.WeakPasswords;
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

    ResponseResult weakPasswordList(MyParam param);
}
