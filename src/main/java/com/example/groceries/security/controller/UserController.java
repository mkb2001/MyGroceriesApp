package com.example.groceries.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path= "/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    
    @GetMapping
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hi User");
    }
}
