package com.iamflubin.budget.auth;

import java.util.List;
import java.util.UUID;

public record CurrentUser(
        UUID id,
        String username,
        String email,
        String fullName,
        List<String> roles
) {
    public CurrentUser {
        roles = roles == null ? List.of() : List.copyOf(roles);
    }

    public boolean isAdmin() {
        return hasAuthority("ROLE_ADMIN");
    }
    
    public boolean hasAuthority(String authority) {
        return roles.contains(authority);
    }
}


