package com.htv.user.service;

import com.htv.security.model.AuthenticatedUser;
import com.htv.security.service.HtvLabUserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HtvLabUserAuthServiceImpl implements HtvLabUserAuthService {
    @Override
    public AuthenticatedUser authenticate(String usernameOrEmail, String rawPassword) {
        return null;
    }

    @Override
    public AuthenticatedUser register(String username, String email, String rawPassword) {
        return null;
    }

    @Override
    public AuthenticatedUser loadByUserId(String userId) {
        return null;
    }
}
