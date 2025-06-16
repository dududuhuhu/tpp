package com.tpp.threat_perception_platform.param;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 系统风险检测参数封装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemRiskParam {

    /**
     * 主机MAC地址，作为设备唯一标识
     */

    private String macAddress;



    /**
     * 主机名称，可选
     */
    private String hostName;

    /**
     * 风险规则ID
     */
    private Integer ruleId;

    /**
     * 风险规则名称
     */
    private String ruleName;

    /**
     * 检测结果，true-安全通过，false-风险
     */
    private Boolean result;

    /**
     * 检测详情信息，字符串形式描述风险或状态
     */
    private String info;

    /**
     * 检测时间，建议传时间戳或ISO格式字符串转Date
     */
    private Date detectedTime;

    /**
     * 风险等级，可选字段，如 LOW, MEDIUM, HIGH
     */
    private String severity;


    public Integer getSystemRisk() {
        return systemRisk;
    }

    private Integer systemRisk;

    /**
     * 任务类型（如 appRisk、systemRisk 等）
     */
    private String type;

    /**
     * 分页：页码
     */
    private Integer page;

    /**
     * 分页：每页数量
     */
    private Integer limit;




}
