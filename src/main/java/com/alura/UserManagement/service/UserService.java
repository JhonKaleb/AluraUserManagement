package com.alura.UserManagement.service;

import com.alura.UserManagement.domain.user.RegisterDTO;
import com.alura.UserManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public ResponseEntity<String> register(RegisterDTO payload) {
        log.info("Registering user: {}", payload.getUsername());

        if (userRepository.findByUsername(payload.getUsername()) != null) {
            log.error("Username already exists");
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(payload.getPassword());
        var user = payload.toUser();
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        log.info("User {} registered", user.getUsername());
        return ResponseEntity.ok().body("User registered successfully!");
    }
}
