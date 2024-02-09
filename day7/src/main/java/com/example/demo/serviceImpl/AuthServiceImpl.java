package com.example.demo.serviceImpl;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.enumerated.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.AuthService;
import com.example.demo.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        System.out.println(request.getEmail());
        Optional<User> isUserExists = userRepository.findByEmail(request.getEmail());
        System.out.println(isUserExists);
        if(isUserExists.isPresent())
        {
            System.out.println("111");
            return RegisterResponse.builder().message("User with email id "+request.getEmail()+"already exists").build();
        }
        System.out.println("here");
        var user=User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .username(request.getPassword())
        .userRole(Role.CUSTOMER)
        .build();
        userRepository.save(user);
        System.out.println("saebed");
        return RegisterResponse.builder().message("User created succesfully").build();
        
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user=userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println("2");
        System.out.println(jwtUtil.generateToken(user));
        var token=jwtUtil.generateToken(user);
        System.out.println(LoginResponse.builder().message("user logged in successfully").token(token).build());
        return LoginResponse.builder().message("user logged in successfully").token(token).build();

    }
    
}
