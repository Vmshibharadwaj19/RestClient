package com.Auth.Jwt;

import com.Auth.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component

public class JwtUtil {


    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {

        this.secretKey = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );
    }



    public String generateToken(User user)
    {
        return Jwts.builder().
                subject(user.getEmail()).claim("userId",user.getId())
                .claim("role",user.getRole()).issuedAt(new Date()).expiration(
                        new Date(System.currentTimeMillis()+1000*60*60)
                ).signWith(secretKey).compact();
    }

    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token).getPayload();

    }

    public String extractEmail(String token) {

        return extractClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role").toString();
    }
    public boolean isTokenValid(String token) {
        try{
            extractClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException ex)
        {
            return false;
        }

    }


}
