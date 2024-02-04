package com.example.groceries.security.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public interface JwtService {
    
    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateUserToken(UserDetails userDetails);

    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateResetPasswordToken(UserDetails userDetails);
}
