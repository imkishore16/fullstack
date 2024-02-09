package com.example.demo.dto;

import org.hibernate.annotations.SecondaryRow;

import lombok.Getter;

@Getter

public class LoginRequest {
    private String email; 
    private String password; 
}
