package com.alura.UserManagement.controller;


import com.alura.UserManagement.domain.user.dtos.AuthenticationDTO;
import com.alura.UserManagement.domain.user.dtos.LoginResponseDTO;
import com.alura.UserManagement.domain.user.dtos.RegisterDTO;
import com.alura.UserManagement.domain.user.dtos.UserDTO;
import com.alura.UserManagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

}
