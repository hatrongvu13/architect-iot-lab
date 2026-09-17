package com.htv.user.service.user;

import com.htv.user.dto.UserUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public Object getMe() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt) {
            return principal;
        }
        return null;
    }

    @Override
    public Object update(UserUpdateRequest request) {
        return null;
    }
}
