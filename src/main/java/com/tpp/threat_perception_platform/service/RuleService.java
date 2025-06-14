package com.tpp.threat_perception_platform.service;
import com.tpp.threat_perception_platform.dao.ApplicationRiskRulesMapper;
import com.tpp.threat_perception_platform.pojo.ApplicationRiskRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RuleService {

    public List<ApplicationRiskRules> getAllApplicationRiskRules();
}
