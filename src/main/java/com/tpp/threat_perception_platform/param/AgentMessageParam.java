package com.tpp.threat_perception_platform.param;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AgentMessageParam {
    private String mac;
    private String message;
    private String sig;

    public AgentMessageParam() {
        this.mac = null;
        this.message = null;
        this.sig = null;
    }

    public AgentMessageParam(String mac, String message, String sig) {
        this.mac = mac;
        this.message = message;
        this.sig = sig;
    }

    public Boolean check() {
        return this.mac != null && this.message != null && this.sig != null;
    }

    // public Boolean init() {
    //     if (this.mac == null || this.message == null || this.sig == null) return false;
    //     ObjectMapper mapper = new ObjectMapper();
    //     try {
    //         this.messageNode = mapper.readTree(this.message);
    //         return true;
    //     } catch (JsonMappingException e) {
    //         return false;
    //     } catch (JsonProcessingException e) {
    //         return false;
    //     }
    // }
}
