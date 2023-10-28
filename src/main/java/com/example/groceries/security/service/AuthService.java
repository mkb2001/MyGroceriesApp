package com.example.groceries.security.service;

import com.example.groceries.security.dto.JwtAuthResponse;
import com.example.groceries.security.dto.RefreshTokenRequest;
import com.example.groceries.security.dto.SignInRequest;
import com.example.groceries.security.dto.SignUpRequest;
import com.example.groceries.security.entity.User;

public interface AuthService {
    
    User signup(SignUpRequest request);
    JwtAuthResponse signin(SignInRequest request);
    JwtAuthResponse refreshToken(RefreshTokenRequest request);
}
