package com.iamflubin.budget.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
class KeycloakCurrentUserProvider implements CurrentUserProvider {
    @Override
    public CurrentUser getCurrentUser() {
        final Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("No authenticated user found");
        }

        final UUID id = UUID.fromString(jwt.getSubject());
        final String username = jwt.getClaimAsString("preferred_username");
        final String email = jwt.getClaimAsString("email");
        final String fullName = buildFullName(jwt);

        final List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return new CurrentUser(id, username, email, fullName, roles);
    }

    private String buildFullName(Jwt jwt) {
        final String firstName = jwt.getClaimAsString("given_name");
        final String lastName = jwt.getClaimAsString("family_name");

        if (firstName == null && lastName == null) return null;
        if (firstName == null) return lastName;
        if (lastName == null) return firstName;

        return firstName + " " + lastName;
    }
}
