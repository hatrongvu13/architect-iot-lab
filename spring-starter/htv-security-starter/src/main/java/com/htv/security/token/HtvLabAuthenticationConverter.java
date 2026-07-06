package com.htv.security.token;

import com.htv.security.HtvSecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HtvLabAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final HtvSecurityProperties properties;

    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.addAll(readClaim(source, properties.getRoleClaim()).stream()
                .map(role -> role.startsWith("ROLE_") ? role : "ROLE_" + role)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));

        authorities.addAll(readClaim(source, properties.getPermissionClaim()).stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));

        return new JwtAuthenticationToken(source, authorities, source.getSubject());
    }

    private Set<String> readClaim(Jwt jwt, String claim) {
        Object value = jwt.getClaim(claim);
        if (value == null) return Set.of();
        if (value instanceof Collection<?> col) return col.stream().map(String::valueOf).collect(Collectors.toSet());
        if (value instanceof String s) return Arrays.stream(s.split(",")).map(String::trim).filter(v -> !v.isBlank()).collect(Collectors.toSet());
        return Set.of();
    }
}
