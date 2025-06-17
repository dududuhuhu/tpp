package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.dao.AgentPublicKeyMapper;
import com.tpp.threat_perception_platform.pojo.AgentPublicKey;
import com.tpp.threat_perception_platform.service.VerifierService;
import com.tpp.threat_perception_platform.utils.SignKeyPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bouncycastle.util.encoders.Base64;
import java.nio.charset.StandardCharsets;

@Service
public class VerifierServiceImpl implements VerifierService {
    private String privateKey = "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgVT59q9bWpS9o/IzS\nRKocsVORPaEvmgiEUCPSKOITCdChRANCAASGoC1DkhmqGtSj/tbUrk1NkDrLkNQ9\nUYzF1W0aLpnhZca/k3ABrPhPmkWzes4ETPZLl28oGfs8nk15JZ3yY2KM";

    private SignKeyPair keyPair;

    @Autowired
    private AgentPublicKeyMapper keyMapper;

    /**
     * Get the SignKeyPair used by this service
     * @return the SignKeyPair
     */
    public SignKeyPair getKeyPair() {
        return this.keyPair;
    }

    public VerifierServiceImpl() {
        this.keyPair = new SignKeyPair();
        this.keyPair.loadPrivateKey(privateKey);
        this.keyPair.generatePublicKey();
    }

    @Override
    public Boolean verifySign(String mac, String message, String signature) {
        if (mac == null || message == null || signature == null) {
            return false;
        }

        AgentPublicKey publicKey = keyMapper.selectByMac(mac);
        if (publicKey == null) {
            return false;
        }

        SignKeyPair agentKeyPair = new SignKeyPair();
        agentKeyPair.loadPublicKey(publicKey.getPemPub());
        return agentKeyPair.verify(message.getBytes(StandardCharsets.UTF_8), Base64.decode(signature));
    }

    @Override
    public Boolean validateAndAddUserPemPublicKey(String mac, String pem_public_key) {
        if (mac == null || pem_public_key == null) return false;
        AgentPublicKey publicKey = keyMapper.selectByMac(mac);
        // 库中没有记录，那么是新用户，直接往库里插
        if (publicKey == null) {
            publicKey = new AgentPublicKey();
            publicKey.setMac(mac);
            publicKey.setPemPub(pem_public_key);
            keyMapper.insert(publicKey);
            return true;
        }

        // 库里有记录，那么需要检查公钥一致
        return publicKey.getPemPub().equals(pem_public_key);
    }

    @Override
    public String signMessage(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        // Convert the message string to bytes
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);

        // Sign the message using the service's keyPair
        byte[] signature = keyPair.sign(messageBytes);

        // Convert the signature bytes to a Base64-encoded string
        return new String(Base64.encode(signature), StandardCharsets.UTF_8);
    }
}
