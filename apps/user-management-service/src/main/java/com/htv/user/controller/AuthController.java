package com.htv.user.controller;

import com.htv.security.model.AuthDtos;
import com.htv.security.model.AuthenticatedUser;
import com.htv.security.service.HtvLabUserAuthService;
import com.htv.security.token.JwtTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@ConditionalOnBean(HtvLabUserAuthService.class)
@RequiredArgsConstructor
public class AuthController {

    private final HtvLabUserAuthService authService;
    private final JwtTokenService tokenService;

    @PostMapping("/register")
    public AuthDtos.TokenResponse register(@Valid @RequestBody AuthDtos.RegisterRequest request) {
        AuthenticatedUser user = authService.register(request.username(), request.email(), request.password());
        return tokenService.issueTokenPair(user, false);
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody AuthDtos.LoginRequest request) {
        AuthenticatedUser user = authService.authenticate(request.usernameOrEmail(), request.password());
        return tokenService.issueTokenPair(user, false);
    }

}
