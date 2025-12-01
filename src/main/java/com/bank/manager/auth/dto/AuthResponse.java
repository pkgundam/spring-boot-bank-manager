package com.bank.manager.auth.dto;

import lombok.Getter;

@Getter
public class AuthResponse {

    private final String accessToken;
    private final String refreshToken;
    private final String tokenType = "Bearer";

    public AuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}