package com.ecom.ecom.Security;

import com.ecom.ecom.JwtUtil.JwtAuthenticationFilter;
import com.ecom.ecom.JwtUtil.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class CustomSecurityFilterChain {
    private final JwtAuthenticationFilter jwtUtil;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )).authorizeHttpRequests(
                        auth -> auth.requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()
                                // READ
                                .requestMatchers(HttpMethod.GET,
                                        "/api/product/**",
                                        "/api/brand/**",
                                        "/api/category/**"
                                ).hasAnyRole("Customer", "Admin")

                                // CREATE
                                .requestMatchers(HttpMethod.POST,
                                        "/api/product/**",
                                        "/api/brand/**",
                                        "/api/category/**"
                                ).hasRole("Admin")

                                // UPDATE
                                .requestMatchers(HttpMethod.PUT,
                                        "/api/product/**",
                                        "/api/brand/**",
                                        "/api/category/**"
                                ).hasRole("Admin")

                                .requestMatchers(HttpMethod.PATCH,
                                        "/api/product/**",
                                        "/api/brand/**",
                                        "/api/category/**"
                                ).hasRole("Admin")

                                // DELETE
                                .requestMatchers(HttpMethod.DELETE,
                                        "/api/product/**",
                                        "/api/brand/**",
                                        "/api/category/**"
                                ).hasRole("Admin")



                                .anyRequest().authenticated()
                ).addFilterBefore(jwtUtil, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}