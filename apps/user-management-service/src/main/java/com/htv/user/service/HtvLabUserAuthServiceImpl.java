package com.htv.user.service;

import com.htv.common.error.ErrorCode;
import com.htv.common.error.ErrorException;
import com.htv.security.model.AuthenticatedUser;
import com.htv.security.service.HtvLabUserAuthService;
import com.htv.user.domain.AppUser;
import com.htv.user.repo.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HtvLabUserAuthServiceImpl implements HtvLabUserAuthService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder encoder;

    @Override
    public AuthenticatedUser authenticate(String usernameOrEmail, String rawPassword) {
        AppUser appUser = appUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElse(null);
        if (Objects.isNull(appUser)) {
            throw new ErrorException(ErrorCode.NOT_FOUND, "Username or email not registered!");
        }
        if (encoder.matches(rawPassword, appUser.getPasswordHash())) {
            return new AuthenticatedUser(appUser.getId(), appUser.getUsername(), appUser.getEmail(), appUser.getRoles(), appUser.getPermissions());
        }
        throw new ErrorException(ErrorCode.UNAUTHORIZED, "Username or password incorrect!");
    }

    @Override
    public AuthenticatedUser register(String username, String email, String rawPassword) {
        if (appUserRepository.existsByUsernameOrEmail(username, email)) {
            throw new ErrorException(ErrorCode.CONFLICT, "Username or email already exist!");
        }
        String hashPassword = encoder.encode(rawPassword);
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setEmail(email);
        appUser.setPasswordHash(hashPassword);
        Set<String> role = new HashSet<>();
        Set<String> permission = new HashSet<>();
        appUser.setRoles(role);
        appUser.setPermissions(permission);
        appUserRepository.save(appUser);
        return new AuthenticatedUser(appUser.getId(), appUser.getUsername(), appUser.getEmail(), role, permission);
    }

    @Override
    public AuthenticatedUser loadByUserId(String userId) {
        return null;
    }
}
