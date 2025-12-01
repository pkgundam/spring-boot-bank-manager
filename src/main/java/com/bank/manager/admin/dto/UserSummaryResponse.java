package com.bank.manager.admin.dto;

import com.bank.manager.auth.entity.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
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

}