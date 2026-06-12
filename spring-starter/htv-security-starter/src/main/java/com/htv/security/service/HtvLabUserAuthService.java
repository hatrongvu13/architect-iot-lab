package com.htv.security.service;

import com.htv.security.model.AuthenticatedUser;

public interface HtvLabUserAuthService {
    AuthenticatedUser authenticate(String usernameOrEmail, String rawPassword);
    AuthenticatedUser register(String username, String email, String rawPassword);
    AuthenticatedUser loadByUserId(String userId);
}
