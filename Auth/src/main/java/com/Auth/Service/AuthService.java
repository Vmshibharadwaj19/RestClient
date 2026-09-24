package com.Auth.Service;



import com.Auth.Dto.LoginRequest;
import com.Auth.Dto.LoginResponse;
import com.Auth.Dto.RegisterRequest;
import com.Auth.Dto.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
}
