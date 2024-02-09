package com.example.demo.config;

import static com.example.demo.enumerated.Role.ADMIN;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Component  //creating a container inside spring bean
@RequiredArgsConstructor //final key word
@SuppressWarnings("null")
public class UserCLI implements CommandLineRunner{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count()>0)
            return;
        var user=User.builder()
            .username("admin")
            .email("admin@gmail.com")
            .password(passwordEncoder.encode("pass"))
            .userRole(ADMIN)
            .build();
        userRepository.save(user);
    }
}
