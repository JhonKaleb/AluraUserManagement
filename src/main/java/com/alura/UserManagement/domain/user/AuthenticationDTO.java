package com.alura.UserManagement.domain.user;

import lombok.Data;

@Data
public class AuthenticationDTO {
    private String username;
    private String password;
}
