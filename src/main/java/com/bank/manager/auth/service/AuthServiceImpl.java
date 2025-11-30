package com.bank.manager.auth.service;

import com.bank.manager.auth.Role;
import com.bank.manager.auth.dto.AuthResponse;
import com.bank.manager.auth.dto.LoginRequest;
import com.bank.manager.auth.dto.RefreshTokenRequest;
import com.bank.manager.auth.dto.RegisterRequest;
import com.bank.manager.auth.exception.EmailAlreadyExistsException;
import com.bank.manager.auth.exception.InvalidCredentialsException;
import com.bank.manager.auth.model.User;
import com.bank.manager.auth.repository.UserRepository;
import com.bank.manager.security.jwt.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepo,
                           PasswordEncoder passwordEncoder,
                           JwtProvider jwtProvider) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User newUser = new User(
                null,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                Set.of(Role.USER)              // default role
        );

        User savedUser = userRepo.save(newUser);
        String accessToken = jwtProvider.generateAccessToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRoles()
        );

        String refreshToken = jwtProvider.generateRefreshToken(savedUser.getId());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordMatches =
                passwordEncoder.matches(request.getPassword(), user.getPasswordHash());
        if (!passwordMatches) {
            throw new InvalidCredentialsException();
        }

        String accessToken = jwtProvider.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRoles()
        );

        String refreshToken = jwtProvider.generateRefreshToken(user.getId());
        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        boolean valid = jwtProvider.validateToken(request.getRefreshToken());
        if (!valid) {
            throw new InvalidCredentialsException(); // invalid or expired refresh
        }

        Long userId = jwtProvider.extractUserId(request.getRefreshToken());
        User user = userRepo.findById(userId)
                .orElseThrow(InvalidCredentialsException::new);
        String newAccessToken = jwtProvider.generateAccessToken(
                user.getId(),
                user.getEmail(),
                user.getRoles()
        );

        String newRefreshToken = jwtProvider.generateRefreshToken(user.getId());
        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    @Override
    public AuthResponse registerAdmin(RegisterRequest request) {
        boolean adminExists = userRepo.findAll().stream()
                .anyMatch(u -> u.getRoles().contains(Role.ADMIN));
        if (adminExists) {
            throw new RuntimeException("Admin already exists");
        }

        User newAdmin = new User(
                null,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                Set.of(Role.ADMIN, Role.USER)
        );

        User savedUser = userRepo.save(newAdmin);
        String accessToken = jwtProvider.generateAccessToken(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRoles()
        );

        String refreshToken = jwtProvider.generateRefreshToken(savedUser.getId());
        return new AuthResponse(accessToken, refreshToken);
    }
}