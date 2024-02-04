package com.example.groceries.security.dto;

import lombok.Data;

@Data
public class UserPasswordReset {
    private String userName;
    private String email;
    private String token;
    private String oldPassword;
    private String newPassword;
}
