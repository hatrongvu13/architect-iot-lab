package com.htv.security.model;

import java.util.Set;

public record AuthenticatedUser(
        String userId,
        String username,
        String email,
        Set<String> roles,
        Set<String> permissions
) {
}
