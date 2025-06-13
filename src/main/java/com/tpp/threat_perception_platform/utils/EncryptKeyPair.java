package com.tpp.threat_perception_platform.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 加密密钥对，使用RSA实现
 */
public class EncryptKeyPair extends KeyPair {
    private static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_CIPHER_ALGORITHM = "RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING";
    private static final String BC_PROVIDER = "BC";
    private static final int DEFAULT_KEY_SIZE = 2048;

    static {
        if (Security.getProvider(BC_PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public EncryptKeyPair() {
        super();
    }

    /**
     * 生成RSA密钥对
     */
    @Override
    public void generate() {
        generate(DEFAULT_KEY_SIZE);
    }

    /**
     * 生成指定大小的RSA密钥对
     * @param keySize 密钥大小（通常为2048或4096）
     */
    public void generate(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM, BC_PROVIDER);
            keyPairGenerator.initialize(keySize);
            java.security.KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.pri = keyPair.getPrivate();
            this.pub = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate RSA key pair", e);
        }
    }

    /**
     * 使用公钥加密数据
     * @param plaintext 明文数据
     * @return 加密后的数据
     */
    public byte[] encrypt(byte[] plaintext) {
        if (this.pub == null) {
            throw new RuntimeException("Public key absent.");
        }

        try {
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_ALGORITHM, BC_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, this.pub);
            return cipher.doFinal(plaintext);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    /**
     * 使用私钥解密数据
     * @param ciphertext 密文数据
     * @return 解密后的数据
     */
    public byte[] decrypt(byte[] ciphertext) {
        if (this.pri == null) {
            throw new RuntimeException("Private key absent.");
        }

        try {
            Cipher cipher = Cipher.getInstance(RSA_CIPHER_ALGORITHM, BC_PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, this.pri);
            return cipher.doFinal(ciphertext);
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }

    @Override
    public void loadPrivateKey(String key) {
        try {
            byte[] keyBytes = Base64.decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM, BC_PROVIDER);
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
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM, BC_PROVIDER);
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
