package com.htv.security.authorization;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class AccessRuleAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final Set<String> requiredRoles;
    private final Set<String> requiredPermissions;
    private final boolean requireMfa;
    private final String mfaClaim;

    public AccessRuleAuthorizationManager(Set<String> requiredRoles, Set<String> requiredPermissions, boolean requireMfa, String mfaClaim) {
        this.requiredRoles = requiredRoles;
        this.requiredPermissions = requiredPermissions;
        this.requireMfa = requireMfa;
        this.mfaClaim = mfaClaim;
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        Authentication auth = authentication.get();
        if (auth == null || !auth.isAuthenticated()) return new AuthorizationDecision(false);
        Set<String> actual = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        boolean rolesOk = requiredRoles.isEmpty() || actual.stream().anyMatch(requiredRoles::contains);
        boolean permissionsOk = requiredPermissions.isEmpty() || actual.containsAll(requiredPermissions);
        boolean mfaOk = !requireMfa || isVerified(auth);
        return new AuthorizationDecision(rolesOk && permissionsOk && mfaOk);
    }

    private boolean isVerified(Authentication auth) {
        if (auth instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            Object value = jwt.getClaim(mfaClaim);
            return Boolean.TRUE.equals(value) || "true".equalsIgnoreCase(String.valueOf(value));
        }
        return false;
    }
}
