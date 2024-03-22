package com.alura.UserManagement.domain.user;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterDTO implements Serializable {
    private String name;
    private String username;
    private String email;
    private String password;
    private UserRole role;

    public User toUser() {
        return new User(name, username, email, role);
    }
}