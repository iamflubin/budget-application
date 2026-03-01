package com.iamflubin.budget.shared.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Component
class KeycloakJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        final var out = new ArrayList<GrantedAuthority>();
        final var ra = jwt.getClaimAsMap("realm_access");
        final Object roles = ra == null ? null : ra.get("roles");
        if (roles instanceof Collection<?> c) {
            c.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                    .distinct()
                    .forEach(r -> out.add(new SimpleGrantedAuthority(r)));
        }
        return out;
    }
}
