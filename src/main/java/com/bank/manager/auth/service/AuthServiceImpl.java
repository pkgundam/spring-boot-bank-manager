package com.bank.manager.auth.service;

import com.bank.manager.auth.dto.AuthResponse;
import com.bank.manager.auth.dto.LoginRequest;
import com.bank.manager.auth.dto.RefreshTokenRequest;
import com.bank.manager.auth.dto.RegisterRequest;
import com.bank.manager.auth.entity.enums.Role;
import com.bank.manager.auth.exception.EmailAlreadyExistsException;
import com.bank.manager.auth.exception.InvalidCredentialsException;
import com.bank.manager.auth.repository.UserRepository;
import com.bank.manager.entity.RefreshTokenEntity;
import com.bank.manager.auth.entity.UserEntity;
import com.bank.manager.repository.RefreshTokenRepository;
import com.bank.manager.security.jwt.JwtProvider;
import com.bank.manager.util.HashUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RefreshTokenRepository refreshRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthServiceImpl(UserRepository userRepo,
                           RefreshTokenRepository refreshRepo,
                           PasswordEncoder passwordEncoder,
                           JwtProvider jwtProvider) {
        this.userRepo = userRepo;
        this.refreshRepo = refreshRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    // -------------------------------------------------------------
    // Register User
    // -------------------------------------------------------------
    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        UserEntity newUser = new UserEntity(
                null,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                Set.of(com.bank.manager.auth.entity.enums.Role.USER),
                true
        );

        UserEntity saved = userRepo.save(newUser);
        return generateTokensForUser(saved);
    }


    // -------------------------------------------------------------
    // Login
    // -------------------------------------------------------------
    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity userEntity = userRepo.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }
        return generateTokensForUser(userEntity);
    }

    // -------------------------------------------------------------
    // Refresh Token Rotation
    // -------------------------------------------------------------
    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        String rawRefreshToken = request.getRefreshToken();
        Long userId = jwtProvider.extractUserId(rawRefreshToken);

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(InvalidCredentialsException::new);

        // fetch all active tokens
        List<RefreshTokenEntity> activeTokens = refreshRepo.findActiveTokens(userId);

        boolean valid = false;
        RefreshTokenEntity matchedToken = null;

        for (RefreshTokenEntity entity : activeTokens) {
            if (passwordEncoder.matches(rawRefreshToken, entity.getTokenHash())) {
                valid = true;
                matchedToken = entity;
                break;
            }
        }

        if (!valid || matchedToken.isRevoked()) {
            throw new InvalidCredentialsException();
        }

        // Rotate token = revoke old one
        matchedToken.setRevoked(true);
        refreshRepo.save(matchedToken);

        // Issue new tokens
        return generateTokensForUser(user);
    }


    // -------------------------------------------------------------
    // Register Admin
    // -------------------------------------------------------------
    @Override
    public AuthResponse registerAdmin(RegisterRequest request) {
        boolean adminExists = userRepo.findAll().stream()
                .anyMatch(u -> u.getRoles().contains(Role.ADMIN));
        if (adminExists) {
            throw new RuntimeException("Admin already exists");
        }

        UserEntity admin = new UserEntity(
                null,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getFullName(),
                Set.of(Role.ADMIN, Role.USER),
                true
        );
        UserEntity saved = userRepo.save(admin);
        return generateTokensForUser(saved);
    }

    // -------------------------------------------------------------
    // Generate & Store hashed refresh token
    // -------------------------------------------------------------
    private AuthResponse generateTokensForUser(UserEntity user) {
        String accessToken = jwtProvider.generateAccessToken(
                user.getId(), user.getEmail(), user.getRoles()
        );

        // raw refresh token
        String rawRefreshToken = jwtProvider.generateRefreshToken(user.getId());

        // hash with SHA-256 instead of BCrypt
        String refreshHash = HashUtil.sha256(rawRefreshToken);

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUser(user);
        entity.setTokenHash(refreshHash);
        entity.setRevoked(false);
        entity.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
        refreshRepo.save(entity);

        return new AuthResponse(accessToken, rawRefreshToken);
    }

}