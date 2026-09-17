package com.htv.user.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserUpdateRequest {
    private String rawPassword;
    private Set<String> roles = new HashSet<>();
    private Set<String> permissions = new HashSet<>();
}
