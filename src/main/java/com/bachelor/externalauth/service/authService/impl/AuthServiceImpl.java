package com.bachelor.externalauth.service.authService.impl;

import com.bachelor.externalauth.model.Jwt;
import com.bachelor.externalauth.model.UserData;
import com.bachelor.externalauth.service.authService.AuthService;
import com.bachelor.externalauth.service.jwtService.JwtService;
import com.bachelor.externalauth.service.userService.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public Jwt authorize(UserData userData) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        UserData user = userRepository.findByUsernameAndPassword(userData.getUsername(), userData.getPassword());
        List<UserData> userDataList = userRepository.findAll();
        if (user == null) {
            throw new IllegalArgumentException("wrong credentials");
        }
        return new Jwt(jwtService.generateAccessToken(user), jwtService.generateRefreshToken(user));
    }
}
