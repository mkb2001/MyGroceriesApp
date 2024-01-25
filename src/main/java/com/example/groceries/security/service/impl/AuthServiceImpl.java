package com.example.groceries.security.service.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.groceries.security.dto.JwtAuthResponse;
import com.example.groceries.security.dto.RefreshTokenRequest;
import com.example.groceries.security.dto.SignInRequest;
import com.example.groceries.security.dto.SignUpRequest;
import com.example.groceries.security.entity.Role;
import com.example.groceries.security.entity.User;
import com.example.groceries.security.repository.UserRepository;
import com.example.groceries.security.service.AuthService;
import com.example.groceries.security.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public JwtAuthResponse signup(SignUpRequest request){
        logger.info("signup request has started");
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        User savedUser = userRepository.save(user);
        
    // Generate JWT token and refresh token for the newly created user
    var jwt = jwtService.generateToken(savedUser);
    var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), savedUser);

    // Return a JwtAuthResponse object containing both tokens
    JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
    jwtAuthResponse.setUsername(request.getUsername());
    jwtAuthResponse.setToken(jwt);
    jwtAuthResponse.setRefreshToken(refreshToken);
    return jwtAuthResponse;
    }

    public JwtAuthResponse signin(SignInRequest request) {
        logger.info("sign in request has started");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setRefreshToken(refreshToken);
        return jwtAuthResponse;
    }

    public JwtAuthResponse refreshToken(RefreshTokenRequest request) {
        logger.info("refresh token request has started");
        String userName = jwtService.extractUserName(request.getToken());
        User user = userRepository.findUserByUsername(userName).orElseThrow();
        if(jwtService.isTokenValid(request.getToken(), user)){
            var jwt = jwtService.generateToken(user);
            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setToken(jwt);
            jwtAuthResponse.setRefreshToken(request.getToken());
            return jwtAuthResponse;
        }
        logger.info("refresh token has been retrieved");
        return null;
        
    }
}
