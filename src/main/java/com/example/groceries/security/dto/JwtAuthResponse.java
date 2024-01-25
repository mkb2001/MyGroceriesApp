package com.example.groceries.security.dto;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String username;
    private String token;
    private String refreshToken;
}
