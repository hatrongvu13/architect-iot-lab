package com.htv.security.model;

import com.htv.security.HtvSecurityProperties;
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

    public record MfaVerifyRequest(@NotBlank String challengeId,
                                   @NotBlank String code) {
    }

    public record MfaSetupRequest(@NotBlank HtvSecurityProperties.MfaMethod method) {
    }

    public record MfaSetupResponse(HtvSecurityProperties.MfaMethod method,
                                   String userId,
                                   String qrUri,
                                   String qrImageBase64,
                                   String emailHint,
                                   long expiresInSeconds) {
    }

    public record MfaChallengeRequest(@NotBlank String userOrEmail,
                                      @NotBlank String password,
                                      HtvSecurityProperties.MfaMethod method){
    }

    public record MfaRequiredResponse(String challengeId,
                                      HtvSecurityProperties.MfaMethod method,
                                      String deliveryHint,
                                      long expiresInSeconds) {
    }

    public record TokenResponse(String accessToken,
                                String refreshToken,
                                String tokenType,
                                long expiresInSeconds,
                                String userId,
                                Set<String> roles,
                                Set<String> permissions,
                                boolean mfaVerified) {
    }

}
