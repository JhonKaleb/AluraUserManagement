package com.alura.UserManagement;


import com.alura.UserManagement.domain.user.*;
import com.alura.UserManagement.domain.user.dtos.AuthenticationDTO;
import com.alura.UserManagement.domain.user.dtos.LoginResponseDTO;
import com.alura.UserManagement.domain.user.dtos.RegisterDTO;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.UserRepository;
import com.alura.UserManagement.service.TokenService;
import com.alura.UserManagement.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    /** ENDPOINT : POST /user/register */

    // Registering a new user with valid username, password, and email
    @Test
    public void test_register_new_user_with_valid_credentials() {
        RegisterDTO registerDTO = new RegisterDTO(
                "test user",
                "testuser",
                "test@email.com",
                "Test1234",
                UserRole.ADMIN
        );

        Mockito.when(userRepository.findUserDetailsByUsername(registerDTO.username())).thenReturn(null);

        ResponseEntity<String> response = userService.register(registerDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Registering a new user with an already existing username
    @Test
    public void test_register_new_user_with_existing_username() {
        RegisterDTO registerDTO = new RegisterDTO(
                "test user",
                "testuser",
                "test@email.com",
                "Test1234",
                UserRole.ADMIN
        );

        Mockito.when(userRepository.findByUsername(registerDTO.username())).thenReturn(new User());

        try {
            userService.register(registerDTO);
            Assertions.fail("Expected ApiRequestException to be thrown");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.User.ALREADY_EXISTS, e.getMessage());
        }
    }

    // Registering a new user with an invalid email
    @Test
    public void test_register_new_user_with_invalid_email() {
        RegisterDTO registerDTO = new RegisterDTO(
                "test user",
                "testuser",
                "invalidEmail",
                "Test1234",
                UserRole.ADMIN
        );

        Mockito.when(userRepository.findByUsername(registerDTO.username())).thenReturn(null);

        try {
            userService.register(registerDTO);
            Assertions.fail("Expected ApiRequestException to be thrown");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.User.INVALID_EMAIL, e.getMessage());
        }
    }

    // Registering a new user with a password containing only lowercase letters
    @Test
    public void test_register_new_user_with_lowercase_password() {
        RegisterDTO registerDTO = new RegisterDTO(
                "test user",
                "testuser",
                "test@email.com",
                "testelow",
                UserRole.ADMIN
        );

        Mockito.when(userRepository.findUserDetailsByUsername(registerDTO.username())).thenReturn(null);

        try {
            userService.register(registerDTO);
            Assertions.fail("Expected ApiRequestException to be thrown");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.User.INVALID_PASSWORD, e.getMessage());
        }
    }

    // Registering a new user with a password containing only uppercase letters
    @Test
    public void test_register_new_user_with_invalid_password() {
        RegisterDTO registerDTO = new RegisterDTO(
                "test user",
                "testuser",
                "test@email.com",
                "TESTUPPER",
                UserRole.ADMIN
        );

        Mockito.when(userRepository.findUserDetailsByUsername(registerDTO.username())).thenReturn(null);

        try {
            userService.register(registerDTO);
            Assertions.fail("Expected ApiRequestException to be thrown");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.User.INVALID_PASSWORD, e.getMessage());
        }
    }


    /** ENDPOINT : POST /user/login */

    @Test
    public void testLoginWithValidCredentials() {
        AuthenticationDTO authenticationDTO = new AuthenticationDTO("testuser", "Test1234");
        String encryptedPassword = new BCryptPasswordEncoder().encode(authenticationDTO.password());

        User user = new User();
        user.setUsername("testuser");
        user.setPassword(encryptedPassword);
        user.setRole(UserRole.ADMIN);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        Mockito.when(tokenService.generateToken(user)).thenReturn("token");

        ResponseEntity<LoginResponseDTO> response = userService.login(authenticationDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
