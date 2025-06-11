package com.tpp.threat_perception_platform.param;

import lombok.Data;

@Data
public class AssetParam {
    private String macAddress;
    private Integer account = 0;
    private Integer service = 0;
    private String serviceType = "ServiceNone";
    private Integer process = 0;
    private Integer app = 0;

    @Override
    public String toString() {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return super.toString();
        }
    }
}
