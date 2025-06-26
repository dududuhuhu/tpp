package com.tpp.threat_perception_platform.param;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 风险消息封装对象，包含任务信息和规则列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskMessageParam {
    private String macAddress;
    private Integer appRisk;
    private Integer systemRisk;
    private String ipAddress;
    private String type;

    private List<RuleParam> rules;
}
