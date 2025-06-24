package com.tpp.threat_perception_platform.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtils {

    /**
     * 生成 SHA-256 摘要的十六进制字符串
     */
    public static String generateSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new RuntimeException("生成SHA-256失败", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }
}
