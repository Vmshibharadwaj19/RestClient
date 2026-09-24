package com.Auth.Dto;


import com.Auth.Enums.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        Role role
) {
}
