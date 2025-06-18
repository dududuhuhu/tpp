package com.tpp.threat_perception_platform.service;

public interface VerifierService {

    // 其他队列的验证，true成功
    Boolean verifySign(String mac, String message, String signature);

    // systeminfo的验证
    Boolean validateAndAddUserPemPublicKey(String mac, String pem_public_key);


    String signMessage(String message);
}
