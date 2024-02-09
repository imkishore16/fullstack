package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.service.AuthService;
import com.example.demo.service.TokenBlacklist;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController{

    private final AuthService authService;
    private final TokenBlacklist tokenBlacklist;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request)
    {
        RegisterResponse response = new RegisterResponse();
        try{
            response =authService.register(request);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        }
        catch(Exception e)
        {
            // response.setMessage("Something went wrong");
            response.setMessage(e.getMessage());;
            return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        try {
            response=authService.login(request);
            return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            LoginResponse.builder().message("something went wrong").token("").build();
            return new ResponseEntity<>(response,HttpStatus.EXPECTATION_FAILED);
        }
    }

    // private static Set<String> blacklist = new HashSet<>();

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
    final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
    String token=authHeader.substring(7);
    tokenBlacklist.addToBlacklist(token);
    System.out.println("dckopasjdcmpdfcklx"+token);
    // Clear session data 

    return ResponseEntity.ok("Logged out successfully");
}   
    
}
