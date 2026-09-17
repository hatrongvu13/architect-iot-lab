package com.htv.user.service.user;

import com.htv.user.dto.UserUpdateRequest;

public interface UserService {
    Object getMe();

    Object update(UserUpdateRequest request);
}
