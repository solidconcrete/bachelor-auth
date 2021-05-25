package com.bachelor.externalauth.security;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@CommonsLog
@Configuration
public class PrivateKeyConfig{

    @Value("${security.jwt-properties.private-key}")
    private Resource keyPath;

    @Bean
    public PrivateKey privateKey() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {
        try (InputStream is = keyPath.getInputStream()){
            byte[] bytes = new byte[is.available()];
            if (is.read(bytes) > 0) {
                KeyFactory kf = KeyFactory.getInstance("RSA");
                return kf.generatePrivate(new PKCS8EncodedKeySpec(bytes));
            }
            return null;
        }
    }
}
