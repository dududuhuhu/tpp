package com.tpp.threat_perception_platform.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgentResponse {
    private String message;
    private String sig;

    public AgentResponse(String message, String sig) {
        this.message = message;
        this.sig = sig;
    }
}
