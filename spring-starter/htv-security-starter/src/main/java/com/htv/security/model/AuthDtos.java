package com.htv.security.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class AuthDtos {
    private AuthDtos() {
    }

    public record RegisterRequest(@NotBlank String username,
                                  @Email @NotBlank String email,
                                  @NotBlank String password) {
    }

    public record LoginRequest(
            @NotBlank String usernameOrEmail,
            @NotBlank String password) {

    }

    public record RefreshRequest(@NotBlank String refreshToken) {
    }

    public record TokenResponse(String accessToken,
                                String refreshToken,
                                String tokenType,
                                long expiresInSeconds,
                                Long userId,
                                Set<String> roles,
                                Set<String> permissions,
                                boolean mfaVerified) {
    }

}
