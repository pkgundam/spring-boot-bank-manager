package com.bank.manager.admin.dto;

import com.bank.manager.auth.Role;

import java.util.Set;

public class UserSummaryResponse {

    private final Long id;
    private final String email;
    private final String fullName;
    private final Set<Role> roles;
    private final boolean enabled;

    public UserSummaryResponse(Long id, String email, String fullName, Set<Role> roles, boolean enabled) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.roles = roles;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

}