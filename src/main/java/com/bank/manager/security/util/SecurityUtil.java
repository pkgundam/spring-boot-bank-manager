package com.bank.manager.security.util;

import com.bank.manager.security.jwt.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return null;
        }
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static Long getCurrentUserId() {
        CustomUserDetails user = getCurrentUser();
        return (user != null) ? user.getId() : null;
    }

    public static boolean isAdmin() {
        CustomUserDetails user = getCurrentUser();
        if (user == null) return false;

        return user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public static boolean isUser() {
        CustomUserDetails user = getCurrentUser();
        if (user == null) return false;

        return user.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
    }

}