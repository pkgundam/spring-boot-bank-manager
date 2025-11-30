package com.bank.manager.auth.controller;

import com.bank.manager.auth.dto.AuthResponse;
import com.bank.manager.auth.dto.LoginRequest;
import com.bank.manager.auth.dto.RefreshTokenRequest;
import com.bank.manager.auth.dto.RegisterRequest;
import com.bank.manager.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${security.master-key}")
    private String masterKey;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            @Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(
            @RequestHeader(value = "X-MASTER-KEY", required = false) String key,
            @Valid @RequestBody RegisterRequest request) {
        if (!masterKey.equals(key)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

}