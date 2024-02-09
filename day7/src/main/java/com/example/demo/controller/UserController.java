package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

import com.example.demo.dto.BasicResponse;
import com.example.demo.dto.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<BasicResponse<UserResponse>> getAllUsers() {
        System.out.println("getUsers");
        BasicResponse<UserResponse> response = new BasicResponse();
        try {
            response = userService.getAllUser();
            return new ResponseEntity<>(response,HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("something wnet woring");
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    
}
