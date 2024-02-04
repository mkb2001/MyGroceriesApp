package com.example.groceries.security.service.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.groceries.Mail.PasswordResetEmail;
import com.example.groceries.security.dto.JwtAuthResponse;
import com.example.groceries.security.dto.RefreshTokenRequest;
import com.example.groceries.security.dto.SignInRequest;
import com.example.groceries.security.dto.SignUpRequest;
import com.example.groceries.security.dto.UserPasswordReset;
import com.example.groceries.security.entity.Role;
import com.example.groceries.security.entity.User;
import com.example.groceries.security.repository.UserRepository;
import com.example.groceries.security.service.AuthService;
import com.example.groceries.security.service.JwtService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordResetEmail resetEmail;

    public JwtAuthResponse signup(SignUpRequest request) {
        logger.info("signup request has started");
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);

        var jwt = jwtService.generateUserToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), savedUser);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setUsername(request.getUsername());
        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    public JwtAuthResponse signin(SignInRequest request) {
        logger.info("sign in request has started");
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        var jwt = jwtService.generateUserToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setUsername(user.getUsername());
        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    public JwtAuthResponse refreshToken(RefreshTokenRequest request) {
        logger.info("refresh token request has started");
        String userName = jwtService.extractUserName(request.getToken());
        User user = userRepository.findUserByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        if (jwtService.isTokenValid(request.getToken(), user)) {
            var jwt = jwtService.generateUserToken(user);
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setToken(jwt);
            jwtAuthResponse.setRefreshToken(request.getToken());
            return jwtAuthResponse;
        }
        logger.info("refresh token has been retrieved");
        return null;

    }

    private String passwordResetEmailLink(UserPasswordReset user, String passwordResetToken)
            throws UnsupportedEncodingException, MessagingException {
        // String url = applicationUrl + "/reset-password?token=" + passwordResetToken;
        resetEmail.sendPasswordResetEmail(user, passwordResetToken);
        return "Password reset token sent successfully";
    }

    public String passwordResetToken(UserPasswordReset request, HttpServletRequest servletRequest)
            throws MessagingException, UnsupportedEncodingException {
        logger.info("password request sent");
        var userName = userRepository.findUserByUsername(request.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));
        var resetToken = jwtService.generateResetPasswordToken(userName);
        String passwordResetLink = passwordResetEmailLink(request, resetToken);
        logger.info("Reset token has been sent via email!");
        return passwordResetLink;
    }

    public String resetPassword(UserPasswordReset request) {
        String token = request.getToken();
        String userName = jwtService.extractUserName(token);
        User user = userRepository.findUserByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username"));
        if (jwtService.isTokenValid(token, user)) {
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);
            logger.info("Password has been reset!");
        }
        return "Password has been reset!";
    }

    public String changePassword(UserPasswordReset requestUtil) {
        User user = userRepository.findUserByUsername(requestUtil.getUserName()).get();

        if (!passwordEncoder.matches(user.getPassword(), requestUtil.getOldPassword())) {
            return "Incorrect old password";
        }
        user.setPassword(passwordEncoder.encode(requestUtil.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
    }

    // public String applicationUrl(HttpServletRequest request) {
    //     return "http://" + request.getServerName() + ":"
    //             + request.getServerPort() + request.getContextPath();
    // }
}
