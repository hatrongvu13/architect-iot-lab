package com.htv.user.service;

import com.htv.security.model.AuthenticatedUser;
import com.htv.security.service.HtvLabUserAuthService;
import com.htv.user.domain.AppUser;
import com.htv.user.repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HtvLabUserAuthServiceImpl implements HtvLabUserAuthService {
    private final AppUserRepository appUserRepository;

    @Override
    public AuthenticatedUser authenticate(String usernameOrEmail, String rawPassword) {
        return null;
    }

    @Override
    public AuthenticatedUser register(String username, String email, String rawPassword) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setEmail(email);
        appUser.setPasswordHash(rawPassword);
        Set<String> role = new HashSet<>();
        Set<String> permission = new HashSet<>();
        return new AuthenticatedUser("01", "admin", "admin@ad.ad", role, permission);
    }

    @Override
    public AuthenticatedUser loadByUserId(String userId) {
        return null;
    }
}
