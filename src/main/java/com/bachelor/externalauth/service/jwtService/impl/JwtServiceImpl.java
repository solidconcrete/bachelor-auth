package com.bachelor.externalauth.service.jwtService.impl;

import com.bachelor.externalauth.model.UserData;
import com.bachelor.externalauth.service.jwtService.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultJwtParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
@CommonsLog
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final PrivateKey privateKey;
    @Value("${jwt-properties.access-expiration}")
    private String accessExpiration;
    @Value("${jwt-properties.refresh-expiration}")
    private String refreshExpiration;

    private final SecretKey secretKey;

    @Override
    public String generateAccessToken(UserData userData) {
        Claims claims;
        String jwtToken = Jwts.builder()
                .signWith(privateKey)
                .setSubject(userData.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(accessExpiration) * 1000))
                .claim("roles", userData.getRoles())
                .compact();
        log.info(jwtToken);
        return jwtToken;
    }

    @Override
    public String generateRefreshToken(UserData userData) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, InvalidKeyException, IllegalBlockSizeException {
        String jwtToken = Jwts.builder()
                .setSubject(userData.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(refreshExpiration) * 1000))
                .claim("roles", userData.getRoles())
                .compact();
        return encryptRefreshToken(jwtToken);
    }

    @Override
    public String encryptRefreshToken(String token) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return URLEncoder.encode(Base64.getEncoder().encodeToString(cipher.doFinal(token.getBytes())));
    }

    @Override
    public String decryptRefreshToken(String token) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(token)));
    }

    @Override
    public String refreshToken(String encryptedRefreshToken) throws IllegalBlockSizeException, InvalidKeyException,
            BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        String refreshToken =  decryptRefreshToken(encryptedRefreshToken);

        DefaultJwtParser parser = new DefaultJwtParser();
        Jwt<?, ?> jwt = parser.parse(refreshToken);
        Claims claims = (Claims) jwt.getBody();

        ArrayList<String> authorities = claims.get("authorities", ArrayList.class);
        String username = claims.getSubject();

        return null;
    }
}
