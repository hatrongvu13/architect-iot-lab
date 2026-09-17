package com.htv.user.controller;

import com.htv.common.contract.ApiResponse;
import com.htv.user.dto.UserUpdateRequest;
import com.htv.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> me() {
        return ResponseEntity.ok(ApiResponse.ok(userService.getMe()));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<?>> update(@RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(userService.update(request)));
    }
}
