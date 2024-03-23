package com.alura.UserManagement;


import com.alura.UserManagement.domain.user.RegisterDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    // Registering a new user with valid username, password, and email
    @Test
    public void test_register_new_user_with_valid_credentials() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("Test1234");
        registerDTO.setEmail("test@hotmail.com");

        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(null);

        ResponseEntity<String> response = userService.register(registerDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Registering a new user with an already existing username
    @Test
    public void test_register_new_user_with_existing_username() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("existinguser");
        registerDTO.setPassword("Test1234");
        registerDTO.setEmail("test@hotmail.com");

        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(new User());

        try {
            userService.register(registerDTO);
            Assertions.fail("Expected ApiRequestException to be thrown");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.User.ALREADY_EXISTS, e.getMessage());
        }
    }

    // Registering a new user with valid username, password, and email, but with numbers in the password
    @Test
    public void test_register_new_user_with_valid_credentials_but_with_numbers_in_password() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("Test1234");
        registerDTO.setEmail("test@hotmail.com");

        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(null);

        ResponseEntity<String> response = userService.register(registerDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Registering a new user with an invalid email
    @Test
    public void test_register_new_user_with_invalid_email() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("Test1234");
        registerDTO.setEmail("invalid_email");

        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(null);

        assertThrows(ApiRequestException.class, () -> {
            userService.register(registerDTO);
        });
    }

    // Registering a new user with a password containing only lowercase letters
    @Test
    public void test_register_new_user_with_lowercase_password() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password");
        registerDTO.setEmail("test@hotmail.com");

        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(null);

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
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("PASSWORD");
        registerDTO.setEmail("test@hotmail.com");

        Mockito.when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(null);

        try {
            userService.register(registerDTO);
            Assertions.fail("Expected ApiRequestException to be thrown");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.User.INVALID_PASSWORD, e.getMessage());
        }
    }
}
