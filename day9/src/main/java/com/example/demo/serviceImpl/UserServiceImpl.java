package com.example.demo.serviceImpl;

import org.springframework.stereotype.Service;

import com.example.demo.dto.BasicResponse;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public BasicResponse<UserResponse> getAllUser() {
        List<User> users=userRepository.findAll();
        List<UserResponse> userResponses =
        users.stream().map(user -> UserResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .email(user.getEmail())
        .build())
        .collect(Collectors.toList());



        return BasicResponse.<UserResponse>builder()
        .message("User data fetcher successfully")
        .data(userResponses)
        .build();
    }

}
