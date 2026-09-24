package com.Auth.ServiceImpl;

import com.Auth.Dto.LoginRequest;
import com.Auth.Dto.LoginResponse;
import com.Auth.Dto.RegisterRequest;
import com.Auth.Dto.UserResponse;
import com.Auth.Entities.User;
import com.Auth.Enums.Role;
import com.Auth.Jwt.JwtUtil;
import com.Auth.Repo.UserRepo;
import com.Auth.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {

        log.debug("Register request: {}", request);
        User user = new User();
        if(repo.existsByEmail(request.email()))
        {
            log.warn("user with this email already exists");
            throw new RuntimeException("User with this email already exists");
        }
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setName(request.name());
        user.setRole(Role.Admin);

        User use=repo.save(user);

   log.info("user created with id {}",use.getId());


        return mapper(use);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if(!repo.existsByEmail(request.email())) {

            throw new RuntimeException("No user with this email id");

        }
        User a=repo.findByEmail(request.email()).orElseThrow();
        if(!passwordEncoder.matches(request.password(),a.getPassword()))
        {
            throw new RuntimeException("invalid email or password");
        }

        String token=jwtUtil.generateToken(a);


        return mapToLoginResponse(a,token);
    }

    UserResponse mapper(User user) {

        return new UserResponse(
                user.getId(),
                   user.getName(),
                user.getEmail(),
                user.getRole()

        );
    }
    private LoginResponse mapToLoginResponse(User user, String token) {

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                token
        );
    }
}
