package com.alura.UserManagement.domain.user.dtos;

import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.domain.user.UserRole;

import java.io.Serializable;

public record RegisterDTO(
        String name,
        String username,
        String email,
        String password,
        UserRole role
    ) implements Serializable {

    public User toUser() {
        return new User(name, username, email, role);
    }
}
