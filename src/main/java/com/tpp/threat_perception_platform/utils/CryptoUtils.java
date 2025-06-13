package com.tpp.threat_perception_platform.utils;

import org.bouncycastle.util.encoders.Base64;

import java.nio.charset.StandardCharsets;

/**
 * 加密工具类，提供简单的接口使用SignKeyPair和EncryptKeyPair
 */
public class CryptoUtils {

    /**
     * 生成签名密钥对
     * @return 签名密钥对
     */
    public static SignKeyPair generateSignKeyPair() {
        SignKeyPair keyPair = new SignKeyPair();
        keyPair.generate();
        return keyPair;
    }

    /**
     * 生成加密密钥对
     * @return 加密密钥对
     */
    public static EncryptKeyPair generateEncryptKeyPair() {
        EncryptKeyPair keyPair = new EncryptKeyPair();
        keyPair.generate();
        return keyPair;
    }

    /**
     * 生成加密密钥对
     * @param keySize 密钥大小
     * @return 加密密钥对
     */
    public static EncryptKeyPair generateEncryptKeyPair(int keySize) {
        EncryptKeyPair keyPair = new EncryptKeyPair();
        keyPair.generate(keySize);
        return keyPair;
    }

    /**
     * 使用签名密钥对签名数据
     * @param keyPair 签名密钥对
     * @param data 要签名的数据
     * @return Base64编码的签名
     */
    public static String sign(SignKeyPair keyPair, String data) {
        byte[] signature = keyPair.sign(data.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encode(signature));
    }

    /**
     * 使用签名密钥对验证签名
     * @param keyPair 签名密钥对
     * @param data 原始数据
     * @param signature Base64编码的签名
     * @return 验证结果
     */
    public static boolean verify(SignKeyPair keyPair, String data, String signature) {
        byte[] signatureBytes = Base64.decode(signature);
        return keyPair.verify(data.getBytes(StandardCharsets.UTF_8), signatureBytes);
    }

    /**
     * 使用加密密钥对加密数据
     * @param keyPair 加密密钥对
     * @param data 要加密的数据
     * @return Base64编码的密文
     */
    public static String encrypt(EncryptKeyPair keyPair, String data) {
        byte[] encrypted = keyPair.encrypt(data.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encode(encrypted));
    }

    /**
     * 使用加密密钥对解密数据
     * @param keyPair 加密密钥对
     * @param encryptedData Base64编码的密文
     * @return 解密后的数据
     */
    public static String decrypt(EncryptKeyPair keyPair, String encryptedData) {
        byte[] encryptedBytes = Base64.decode(encryptedData);
        byte[] decrypted = keyPair.decrypt(encryptedBytes);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

    /**
     * 从PEM格式的私钥和公钥创建签名密钥对
     * @param privateKey PEM格式的私钥
     * @param publicKey PEM格式的公钥
     * @return 签名密钥对
     */
    public static SignKeyPair createSignKeyPair(String privateKey, String publicKey) {
        SignKeyPair keyPair = new SignKeyPair();
        if (privateKey != null) {
            keyPair.loadPrivateKey(privateKey);
        }
        if (publicKey != null) {
            keyPair.loadPublicKey(publicKey);
        }
        return keyPair;
    }

    /**
     * 从PEM格式的私钥和公钥创建加密密钥对
     * @param privateKey PEM格式的私钥
     * @param publicKey PEM格式的公钥
     * @return 加密密钥对
     */
    public static EncryptKeyPair createEncryptKeyPair(String privateKey, String publicKey) {
        EncryptKeyPair keyPair = new EncryptKeyPair();
        if (privateKey != null) {
            keyPair.loadPrivateKey(privateKey);
        }
        if (publicKey != null) {
            keyPair.loadPublicKey(publicKey);
        }
        return keyPair;
    }

    /**
     * 使用ECDH生成AES密钥
     * @param keyPair 签名密钥对
     * @param otherPublicKey 对方的公钥
     * @return Base64编码的AES密钥
     */
    public static String generateSharedAESKey(SignKeyPair keyPair, String otherPublicKey) {
        byte[] aesKey = keyPair.generateAesKey(otherPublicKey);
        return new String(Base64.encode(aesKey));
    }
}