package com.bank.manager.auth.model;

import com.bank.manager.auth.Role;

import java.time.LocalDateTime;
import java.util.Set;

public class User {

    private Long id;
    private String email;
    private String passwordHash;
    private String fullName;
    private Set<Role> roles;
    private LocalDateTime createdAt;
    private Boolean enabled;

    public User() {
    }

    public User(Long id, String email, String passwordHash, String fullName, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.roles = roles;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getFullName() {
        return fullName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}