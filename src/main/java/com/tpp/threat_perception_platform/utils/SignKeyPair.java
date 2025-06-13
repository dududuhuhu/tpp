package com.tpp.threat_perception_platform.utils;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPrivateKeySpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.util.encoders.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyAgreement;
import java.security.MessageDigest;

/**
 * 签名密钥对，使用ECC实现
 */
public class SignKeyPair extends KeyPair {
    private static final String EC_ALGORITHM = "ECDSA";
    private static final String EC_CURVE = "secp256r1"; // Same as P-256
    private static final String SIGNATURE_ALGORITHM = "SHA256withECDSA";
    private static final String BC_PROVIDER = "BC";

    static {
        if (Security.getProvider(BC_PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public SignKeyPair() {
        super();
    }

    /**
     * 生成ECC密钥对
     */
    @Override
    public void generate() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EC_ALGORITHM, BC_PROVIDER);
            ECNamedCurveParameterSpec parameterSpec = ECNamedCurveTable.getParameterSpec(EC_CURVE);
            keyPairGenerator.initialize(parameterSpec);
            
            java.security.KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.pri = keyPair.getPrivate();
            this.pub = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate ECC key pair", e);
        }
    }

    /**
     * 使用私钥签名数据
     * @param data 要签名的数据
     * @return 签名
     */
    public byte[] sign(byte[] data) {
        if (this.pri == null) {
            throw new RuntimeException("Private key absent.");
        }
        
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM, BC_PROVIDER);
            signature.initSign(this.pri);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign data", e);
        }
    }

    /**
     * 使用公钥验证签名
     * @param data 原始数据
     * @param signature 签名
     * @return 验证结果
     */
    public boolean verify(byte[] data, byte[] signature) {
        if (this.pub == null) {
            throw new RuntimeException("Public key absent.");
        }
        
        try {
            Signature sig = Signature.getInstance(SIGNATURE_ALGORITHM, BC_PROVIDER);
            sig.initVerify(this.pub);
            sig.update(data);
            return sig.verify(signature);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 生成AES密钥（使用ECDH密钥协商）
     * @param pemPubKey 对方的PEM格式公钥
     * @return 生成的AES密钥
     */
    public byte[] generateAesKey(String pemPubKey) {
        try {
            // 解析对方的公钥
            byte[] pubKeyBytes = Base64.decode(pemPubKey);
            KeyFactory keyFactory = KeyFactory.getInstance(EC_ALGORITHM, BC_PROVIDER);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            
            // 执行ECDH密钥协商
            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH", BC_PROVIDER);
            keyAgreement.init(this.pri);
            keyAgreement.doPhase(publicKey, true);
            byte[] sharedSecret = keyAgreement.generateSecret();
            
            // 对共享密钥进行哈希处理，生成AES密钥
            MessageDigest digest = MessageDigest.getInstance("SHA-256", BC_PROVIDER);
            return digest.digest(sharedSecret);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate AES key", e);
        }
    }

    /**
     * 从私钥生成公钥
     */
    public void generatePublicKey() {
        if (this.pri != null && this.pub == null) {
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(EC_ALGORITHM, BC_PROVIDER);
                this.pub = keyFactory.generatePublic(
                    new X509EncodedKeySpec(
                        keyFactory.getKeySpec(this.pri, PKCS8EncodedKeySpec.class).getEncoded()
                    )
                );
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate public key from private key", e);
            }
        } else if (this.pri == null) {
            throw new RuntimeException("Private key absent.");
        }
    }

    @Override
    public void loadPrivateKey(String key) {
        try {
            byte[] keyBytes = Base64.decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(EC_ALGORITHM, BC_PROVIDER);
            this.pri = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
            this.pemPri = key;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load private key", e);
        }
    }

    @Override
    public void loadPublicKey(String key) {
        try {
            byte[] keyBytes = Base64.decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(EC_ALGORITHM, BC_PROVIDER);
            this.pub = keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
            this.pemPub = key;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load public key", e);
        }
    }

    @Override
    public void serialize() {
        if (this.pri != null) {
            this.pemPri = keySerialize(this.pri, true);
        }
        
        if (this.pub != null) {
            this.pemPub = keySerialize(this.pub, false);
        }
    }
}