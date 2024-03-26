package com.alura.UserManagement.domain.user.dtos;

import com.alura.UserManagement.domain.user.User;

public record UserDTO(String name, String email, String role) {


    public static UserDTO fromUser(User user) {
        return new UserDTO(user.getName(), user.getEmail(), user.getRole().getCode());
    }
}
