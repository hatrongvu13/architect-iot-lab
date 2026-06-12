package com.htv.security.model;

import com.htv.security.HtvSecurityProperties;

import java.util.Set;

public record AuthenticatedUser(
        String userId,
        String username,
        String email,
        Set<String> roles,
        Set<String> permissions,
        boolean mfaEnabled,
        HtvSecurityProperties.MfaMethod preferredMfaMethod
) {
}
