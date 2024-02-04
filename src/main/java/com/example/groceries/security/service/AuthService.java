package com.example.groceries.security.service;

import com.example.groceries.security.dto.JwtAuthResponse;
import com.example.groceries.security.dto.RefreshTokenRequest;
import com.example.groceries.security.dto.SignInRequest;
import com.example.groceries.security.dto.SignUpRequest;
import com.example.groceries.security.dto.UserPasswordReset;

import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    
    JwtAuthResponse signup(SignUpRequest request);
    JwtAuthResponse signin(SignInRequest request);
    JwtAuthResponse refreshToken(RefreshTokenRequest request);
    String passwordResetToken(UserPasswordReset request, HttpServletRequest servletRequest) throws MessagingException, UnsupportedEncodingException;
    String resetPassword(UserPasswordReset request);
    String changePassword(UserPasswordReset requestUtil);
}
