package com.bachelor.externalauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class Jwt {
    private String accessToken;
    private String refreshToken;
}
