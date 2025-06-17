package com.tpp.threat_perception_platform.service;

public interface VerifierService {
    Boolean verifySign(String mac, String message, String signature);

    Boolean validateAndAddUserPemPublicKey(String mac, String pem_public_key);

    String signMessage(String message);
}
