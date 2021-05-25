package com.bachelor.externalauth.controller;

import com.bachelor.externalauth.model.Jwt;
import com.bachelor.externalauth.model.UserData;
import com.bachelor.externalauth.service.authService.AuthService;
import com.bachelor.externalauth.service.jwtService.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@CommonsLog
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authorize;


    @GetMapping("/refresh")
    public String refreshAccessToken(@RequestHeader("Refresh-token") String refreshToken, HttpServletResponse response){
        log.info(refreshToken);
        try {
            return jwtService.refreshToken(refreshToken);
        } catch (ExpiredJwtException expired) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("catched exception");
            return "Jwt Expired";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.info("catched exception");
            return "bad refresh token";
        }
    }
    @PostMapping("/login")
    public Jwt test(@RequestBody UserData userData) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return authorize.authorize(userData);
    }
}
