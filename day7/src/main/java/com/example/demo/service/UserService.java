package com.example.demo.service;

import com.example.demo.dto.BasicResponse;
import com.example.demo.dto.UserResponse;

public interface UserService {

    BasicResponse<UserResponse> getAllUser();
    
}
