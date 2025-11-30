package com.bank.manager.auth.service;

import com.bank.manager.auth.dto.AuthResponse;
import com.bank.manager.auth.dto.LoginRequest;
import com.bank.manager.auth.dto.RefreshTokenRequest;
import com.bank.manager.auth.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);

    AuthResponse registerAdmin(RegisterRequest request);

}