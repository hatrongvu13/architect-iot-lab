package com.htv.security.web;

import com.htv.common.error.ErrorCode;
import com.htv.common.error.ErrorException;
import com.htv.security.HtvSecurityProperties;
import com.htv.security.model.AuthDtos;
import com.htv.security.model.AuthenticatedUser;
import com.htv.security.service.HtvLabUserAuthService;
import com.htv.security.service.MfaService;
import com.htv.security.service.RefreshTokenStore;
import com.htv.security.token.JwtTokenService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@ConditionalOnProperty(
        prefix = "htv.security",
        name = "mode",
        havingValue = "auth_server"
)
@ConditionalOnBean(HtvLabUserAuthService.class)
@RequiredArgsConstructor
public class AuthController {

    private final HtvLabUserAuthService authService;
    private final JwtTokenService tokenService;
    private final RefreshTokenStore refreshTokenStore;
    private final MfaService mfaService;
    private final HtvSecurityProperties properties;

    @PostMapping("/register")
    public AuthDtos.TokenResponse register(@Valid @RequestBody AuthDtos.RegisterRequest request) {
        AuthenticatedUser user = authService.register(request.username(), request.email(), request.password());
        return tokenService.issueTokenPair(user, false);
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody AuthDtos.LoginRequest request) {
        AuthenticatedUser user = authService.authenticate(request.usernameOrEmail(), request.password());
        if (shouldAskMfa(user)) {
            HtvSecurityProperties.MfaMethod method = user.preferredMfaMethod() != null ? user.preferredMfaMethod() : properties.getMfa().getAllowedMfaMethods().get(0);
            MfaService.MfaChallenge challenge = mfaService.createChallenge(user, method);
            return new AuthDtos.MfaRequiredResponse(challenge.challengeId(), challenge.method(), challenge.deliveryHint(), challenge.expiresInSeconds());
        }

        return tokenService.issueTokenPair(user, false);
    }

    @PostMapping("/mfa/setup")
    public AuthDtos.MfaSetupResponse setup(Authentication authentication, @Valid @RequestBody AuthDtos.MfaSetupRequest request) {
        ensureMfaEnabled();
        ensureMethodAllowed(request.method());
        AuthenticatedUser user = authService.loadByUserId(authentication.getName());
        MfaService.MfaSetup mfaSetup = mfaService.beginSetup(user, request.method());
        return new AuthDtos.MfaSetupResponse(mfaSetup.method(), mfaSetup.setupId(), mfaSetup.qrUri(), mfaSetup.qrImageBase64(), mfaSetup
                .emailHint(), mfaSetup.expiresInSeconds());
    }

    private void ensureMethodAllowed(HtvSecurityProperties.@NotBlank MfaMethod method) {
        if (!properties.getMfa().getAllowedMfaMethods().contains(method))
            throw new ErrorException(ErrorCode.VALIDATION_ERROR, "MFA method not allowed");
    }

    private void ensureMfaEnabled() {
        if (!properties.getMfa().isEnabled()) throw new ErrorException(ErrorCode.VALIDATION_ERROR, "MFA is disable");
    }

    private boolean shouldAskMfa(AuthenticatedUser user) {
        return properties.getMfa().isEnabled() && user.mfaEnabled();
    }

}
