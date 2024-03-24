package com.alura.UserManagement.service;

import com.alura.UserManagement.domain.user.AuthenticationDTO;
import com.alura.UserManagement.domain.user.LoginResponseDTO;
import com.alura.UserManagement.domain.user.RegisterDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<String> register(RegisterDTO payload) {
        log.info("Registering user: {}", payload.getUsername());

        if (userRepository.findByUsername(payload.getUsername()) != null) {
            log.error("Username already exists");
            throw new ApiRequestException(ErrorMessages.User.ALREADY_EXISTS);
        }

        if (!isPasswordValid(payload.getPassword())) {
            log.error("Password does not meet the requirements");
            throw new ApiRequestException(ErrorMessages.User.INVALID_PASSWORD);
        }

        if (!isEmailValid(payload.getEmail())) {
            log.error("Email does not meet the requirements");
            throw new ApiRequestException(ErrorMessages.User.INVALID_EMAIL);
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(payload.getPassword());
        var user = payload.toUser();
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        log.info("User {} registered", user.getUsername());
        return ResponseEntity.ok().body("User registered successfully!");
    }

    /**
     * Validates if the password is valid
     * Validation rules:
     * - At least 8 characters
     * - At least one uppercase letter
     * - At least one lowercase letter
     * - At least one number
     *
     * @param password
     * @return true if the password is valid, false otherwise
     */
    private boolean isPasswordValid(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
    }

    private boolean isEmailValid(String email) {
        return email.matches("^(.+)@(.+)$");
    }

    public ResponseEntity<LoginResponseDTO> login(AuthenticationDTO payload) {
        log.info("Logging in user: {}", payload.getUsername());
        var usernamePassword = new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
