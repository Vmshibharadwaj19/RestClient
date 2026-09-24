package com.Auth.Dto;


import com.Auth.Enums.Role;

public record LoginResponse(
        Long userId,
        String name,
        String email,
        Role role,
        String token
) {
}
