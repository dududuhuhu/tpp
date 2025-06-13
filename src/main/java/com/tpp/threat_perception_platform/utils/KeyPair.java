package com.tpp.threat_perception_platform.utils;

import org.bouncycastle.util.encoders.Base64;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.nio.charset.StandardCharsets;

/**
 * 密钥对基类，提供密钥对的基本操作
 */
public abstract class KeyPair {
    protected PrivateKey pri;
    protected PublicKey pub;
    protected String pemPri;
    protected String pemPub;

    public KeyPair() {
        this.pri = null;
        this.pub = null;
        this.pemPri = null;
        this.pemPub = null;
    }

    /**
     * 生成密钥对
     */
    public abstract void generate();

    /**
     * 获取公钥
     * @return 公钥
     */
    public PublicKey getPublicKey() {
        if (this.pub != null) {
            return this.pub;
        } else {
            throw new RuntimeException("Public key absent.");
        }
    }

    /**
     * 获取私钥
     * @return 私钥
     */
    public PrivateKey getPrivateKey() {
        if (this.pri != null) {
            return this.pri;
        } else {
            throw new RuntimeException("Private key absent.");
        }
    }

    /**
     * 获取PEM格式的公钥
     * @return PEM格式的公钥字符串
     */
    public String getPemPublicKey() {
        if (this.pemPub != null) {
            return this.pemPub;
        } else if (this.pub != null) {
            this.serialize();
            return this.pemPub;
        } else {
            throw new RuntimeException("Public key absent.");
        }
    }

    /**
     * 获取PEM格式的私钥
     * @return PEM格式的私钥字符串
     */
    public String getPemPrivateKey() {
        if (this.pemPri != null) {
            return this.pemPri;
        } else if (this.pri != null) {
            this.serialize();
            return this.pemPri;
        } else {
            throw new RuntimeException("Private key absent.");
        }
    }

    /**
     * 加载PEM格式的私钥
     * @param key PEM格式的私钥字符串
     */
    public abstract void loadPrivateKey(String key);

    /**
     * 加载PEM格式的公钥
     * @param key PEM格式的公钥字符串
     */
    public abstract void loadPublicKey(String key);

    /**
     * 序列化密钥对为PEM格式
     */
    public abstract void serialize();

    /**
     * 将密钥序列化为PEM格式
     * @param key 密钥
     * @param isPrivate 是否为私钥
     * @return PEM格式的密钥字符串
     */
    protected static String keySerialize(Object key, boolean isPrivate) {
        try {
            byte[] encoded;
            if (isPrivate) {
                encoded = ((PrivateKey) key).getEncoded();
            } else {
                encoded = ((PublicKey) key).getEncoded();
            }
            return new String(Base64.encode(encoded));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize key", e);
        }
    }
}