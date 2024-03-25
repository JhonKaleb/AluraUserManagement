package com.alura.UserManagement.domain.user.dtos;

import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.domain.user.UserRole;

import java.io.Serializable;

public record UserDTO(String name, String email, UserRole role) implements Serializable {

    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getName(), user.getEmail(), user.getRole());
    }
}
