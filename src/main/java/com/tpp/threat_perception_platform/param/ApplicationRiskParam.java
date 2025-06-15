package com.tpp.threat_perception_platform.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 应用风险参数对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRiskParam {

    /**
     * 规则ID
     */
    private Integer ruleId;

    /**
     * 风险名称
     */
    private String riskName;

    /**
     * 风险类型
     */
    private String riskType;

    /**
     * 风险等级
     */
    private Integer riskLevel;

    /**
     * 目标主机
     */
    private String targetHost;

    /**
     * 目标URL
     */
    private String targetUrl;

    /**
     * 检测时间
     */
    private String detectionTime;

    /**
     * 是否存在风险：0-否，1-是
     */
    private Integer isRisky;

    /**
     * 风险详情
     */
    private String riskDetail;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 修复建议
     */
    private String remediationAdvice;

    /**
     * 状态（pending, confirmed, fixed, false_positive）
     */
    private String status;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 设备 MAC 地址
     */
    private String macAddress;

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

    public String getIpAddress() {
        return ipAddress;
    }

    private String ipAddress;

    private Integer AppRisk;
}
