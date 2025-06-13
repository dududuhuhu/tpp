package com.tpp.threat_perception_platform.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.NoSuchPaddingException;

/**
 * 对称加密类，使用AES（CBC模式）实现
 */
public class Encryption {
    private static final String BC_PROVIDER = "BC";
    private static final String AES_ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final int KEY_SIZE = 256; // 256 bits = 32 bytes
    private static final int IV_SIZE = 16; // 128 bits = 16 bytes

    static {
        if (Security.getProvider(BC_PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private byte[] key;

    public Encryption() {
        this.key = null;
    }

    /**
     * 生成AES密钥（256位）
     */
    public void generate() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES", BC_PROVIDER);
            keyGenerator.init(KEY_SIZE);
            SecretKey secretKey = keyGenerator.generateKey();
            this.key = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to generate AES key", e);
        }
    }

    /**
     * 加载AES密钥
     * @param key 32字节的密钥
     */
    public void loadKey(byte[] key) {
        if (key.length != 32) {
            throw new RuntimeException("Invalid key length.");
        }
        this.key = key.clone();
    }

    /**
     * 获取当前密钥
     * @return 当前密钥
     */
    public byte[] getKey() {
        if (this.key == null) {
            throw new RuntimeException("Key absent.");
        }
        return this.key.clone();
    }

    /**
     * 加密数据
     * @param plaintext 明文数据
     * @param iv 初始化向量（可选，如果不提供则随机生成）
     * @return 包含密文和IV的元组
     */
    public EncryptionResult encrypt(byte[] plaintext, byte[] iv) {
        if (this.key == null) {
            throw new RuntimeException("Key absent.");
        }

        byte[] initVector;
        if (iv == null) {
            // 生成随机IV
            initVector = new byte[IV_SIZE];
            SecureRandom random = new SecureRandom();
            random.nextBytes(initVector);
        } else {
            if (iv.length != IV_SIZE) {
                throw new RuntimeException("Invalid initialization vector length.");
            }
            initVector = iv.clone();
        }

        try {
            // 创建密钥规格和IV规格
            SecretKeySpec keySpec = new SecretKeySpec(this.key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);

            // 创建并初始化Cipher
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM, BC_PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            // 对数据进行PKCS7填充并加密
            byte[] ciphertext = cipher.doFinal(plaintext);
            
            return new EncryptionResult(ciphertext, initVector);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException |
                IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Failed to encrypt data", e);
        }
    }

    /**
     * 加密数据（使用随机生成的IV）
     * @param plaintext 明文数据
     * @return 包含密文和IV的元组
     */
    public EncryptionResult encrypt(byte[] plaintext) {
        return encrypt(plaintext, null);
    }

    /**
     * 解密数据
     * @param ciphertext 密文数据
     * @param iv 初始化向量
     * @return 解密后的数据
     */
    public byte[] decrypt(byte[] ciphertext, byte[] iv) {
        if (this.key == null) {
            throw new RuntimeException("Key absent.");
        }

        if (iv.length != IV_SIZE) {
            throw new RuntimeException("Invalid initialization vector length.");
        }

        try {
            // 创建密钥规格和IV规格
            SecretKeySpec keySpec = new SecretKeySpec(this.key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            // 创建并初始化Cipher
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM, BC_PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

            // 解密数据并去除填充
            return cipher.doFinal(ciphertext);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException |
                InvalidKeyException | InvalidAlgorithmParameterException |
                IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Failed to decrypt data", e);
        }
    }

    /**
     * 加密结果类，包含密文和IV
     */
    public static class EncryptionResult {
        private final byte[] ciphertext;
        private final byte[] iv;

        public EncryptionResult(byte[] ciphertext, byte[] iv) {
            this.ciphertext = ciphertext;
            this.iv = iv;
        }

        public byte[] getCiphertext() {
            return ciphertext;
        }

        public byte[] getIv() {
            return iv;
        }
    }
}