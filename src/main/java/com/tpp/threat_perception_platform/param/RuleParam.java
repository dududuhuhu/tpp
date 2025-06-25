package com.tpp.threat_perception_platform.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用风险规则对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleParam {
    private Integer id;
    private String detectionPayload;
    private String riskName;
    private String riskType;
    private String riskLevel;
    private String detectionMethod;
    private String rulePath;
    private String rulePayload;
    private String remediationAdvice;
    private String vulName;
    private Integer vulLevel;
    private String vulType;
    private String vulPath;
    private String vulPayload;
    private String vulFlag;
    private String vulRequestType;

}
