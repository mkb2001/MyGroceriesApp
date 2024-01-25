package com.example.groceries.security.service;

import com.example.groceries.security.dto.JwtAuthResponse;
import com.example.groceries.security.dto.RefreshTokenRequest;
import com.example.groceries.security.dto.SignInRequest;
import com.example.groceries.security.dto.SignUpRequest;

public interface AuthService {
    
    JwtAuthResponse signup(SignUpRequest request);
    JwtAuthResponse signin(SignInRequest request);
    JwtAuthResponse refreshToken(RefreshTokenRequest request);
}
