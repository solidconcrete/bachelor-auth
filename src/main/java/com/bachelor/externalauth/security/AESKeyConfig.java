package com.bachelor.externalauth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Configuration
public class AESKeyConfig {

    @Value("${security.jwt-properties.symmetric-key}")
    private String key;

    @Bean
    public SecretKey secretKey() throws NoSuchAlgorithmException {

        byte[] keyBytes = key.getBytes();
        MessageDigest sha = null;
        sha = MessageDigest.getInstance("SHA-1");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16);

        return new SecretKeySpec(keyBytes, "AES");
    }
}
