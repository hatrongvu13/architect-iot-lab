package com.htv.common.security;

import java.util.Set;

public class AuthDtos {

    private AuthDtos() {
    }

    public record RegisterRequest(String username, String email, String password) {
    }

    public record LoginRequest(String username, String password) {
    }

    public record TokenResponse(String accessToken, String refreshToken, long expiresInSeconds) {
    }

    public record UserPrincipal(String userId, String username, Set<String> roles, Set<String> permission) {
    }
}
