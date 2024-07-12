package com.openmall.common.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * RSA 工具类
 *
 * @author wuxuan
 * @since 2024/7/5 16:52:16
 */
public class RSAUtils {
    /**
     * 生成rsa密钥对
     *
     * @return rsa密钥对
     */
    public static KeyPair generateRsaKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
