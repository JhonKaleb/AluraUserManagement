package com.alura.UserManagement.controller;


import com.alura.UserManagement.domain.user.AuthenticationDTO;
import com.alura.UserManagement.domain.user.LoginResponseDTO;
import com.alura.UserManagement.domain.user.RegisterDTO;
import com.alura.UserManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO payload) {
        return userService.login(payload);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO payload) {
        return userService.register(payload);
    }

}
