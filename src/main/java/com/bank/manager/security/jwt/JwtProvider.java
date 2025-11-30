package com.bank.manager.security.jwt;

import com.bank.manager.auth.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final Key signingKey;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateAccessToken(Long userId, String email, Set<Role> roles) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtProperties.getExpirationMs());

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("roles", roles.stream().map(Enum::name).collect(Collectors.toList()))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        Instant now = Instant.now();
        Instant expiry = now.plusMillis(jwtProperties.getRefreshExpirationMs());

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long extractUserId(String token) {
        return Long.valueOf(extractAllClaims(token).getSubject());
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    @SuppressWarnings("unchecked")
    public Set<Role> extractRoles(String token) {
        List<String> roleNames = extractAllClaims(token).get("roles", List.class);
        return roleNames
                .stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}