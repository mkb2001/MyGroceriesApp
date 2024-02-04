package com.example.groceries.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.groceries.security.dto.JwtAuthResponse;
import com.example.groceries.security.dto.RefreshTokenRequest;
import com.example.groceries.security.dto.SignInRequest;
import com.example.groceries.security.dto.SignUpRequest;
import com.example.groceries.security.dto.UserPasswordReset;
import com.example.groceries.security.service.AuthService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import java.io.UnsupportedEncodingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping(path = "/signup")
    public ResponseEntity<JwtAuthResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping(path = "/signin")
    public ResponseEntity<JwtAuthResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authService.signin(request));
    }

    @PostMapping(path = "/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @GetMapping("/user")
    public String helloUser() {
        return "Hello User";
    }

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserPasswordReset user,
            final HttpServletRequest servletRequest) throws UnsupportedEncodingException, MessagingException {
        return ResponseEntity.ok(authService.passwordResetToken(user, servletRequest));
    }

    @PostMapping(path = "/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody UserPasswordReset request) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }
}
