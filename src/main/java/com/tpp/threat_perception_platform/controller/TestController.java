package com.tpp.threat_perception_platform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpp.threat_perception_platform.param.AgentMessageParam;
import com.tpp.threat_perception_platform.response.AgentResponse;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.VerifierService;
import com.tpp.threat_perception_platform.utils.SignKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private VerifierService verifierService;

    @PostMapping("/test/testValidateAndAddUserPemPublicKey")
    public AgentResponse testValidateAndAddUserPemPublicKey(@RequestBody AgentMessageParam param) {
        ObjectMapper mapper = new ObjectMapper();
        Integer status = 0;
        try {
            if (!param.init()) {
                Map<String, Object> map = new HashMap<>();
                map.put("status", 0);
                String json_message = mapper.writeValueAsString(map);
                return new AgentResponse(500, json_message, verifierService.signMessage(json_message));
            }
            SignKeyPair keyPair = new SignKeyPair();
            String pemPublicKey = param.getMessageNode().get("pub").asText();
            keyPair.loadPublicKey(pemPublicKey);
            byte[] _message = param.getMessage().getBytes(StandardCharsets.UTF_8);
            byte[] _sig = SignKeyPair.translateStrToBytes(param.getSig());
            if (!keyPair.verify(_message, _sig)) {
                status = 0;
            } else {
                if (!verifierService.validateAndAddUserPemPublicKey(param.getMac(), pemPublicKey))
                    status = 0;
                else
                    status = 1;
            }
        }
        catch (JsonMappingException e) {
            status = 0;
        }
        catch (JsonProcessingException e) {
            status = 0;
        }
        finally {
            Map<String, Object> map = new HashMap<>();
            map.put("status", status);
            String json_message = null;
            try {
                json_message = mapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return new AgentResponse(status == 1 ? 200 : 500, json_message, verifierService.signMessage(json_message));
        }
    }

    @PostMapping("/test/testVerifySign")
    public AgentResponse testVerifySign(@RequestBody AgentMessageParam param) {
        ObjectMapper mapper = new ObjectMapper();
        Integer status = 0;
        try {
            if (!param.init()) {
                Map<String ,Object> map = new HashMap<>();
                map.put("status", 0);
                String json_message = mapper.writeValueAsString(map);
                return new AgentResponse(500, json_message, verifierService.signMessage(json_message));
            }
            if (!verifierService.verifySign(param.getMac(), param.getMessage(), param.getSig()))
                status = 0;
            else status = 1;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        finally {
            Map<String, Object> map = new HashMap<>();
            map.put("status", status);
            String json_message = null;
            try {
                json_message = mapper.writeValueAsString(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return new AgentResponse(status == 1 ? 200 : 500, json_message, verifierService.signMessage(json_message));
        }
    }
}
