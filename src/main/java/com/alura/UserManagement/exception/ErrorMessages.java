package com.alura.UserManagement.exception;

public class ErrorMessages {
    public static class User {
        public static final String ALREADY_EXISTS   = "User with username provided already exists";
        public static final String INVALID_PASSWORD = "Password does not meet the requirements" +
                "- At least 8 characters" +
                "- At least one uppercase letter" +
                "- At least one lowercase letter" +
                "- At least one number";
        public static final String INVALID_EMAIL    = "Email does not meet the requirements";
        public static final String NOT_FOUND = "User not found";
    }

    public static class Token {
        public static final String CREATION_ERROR = "Error creating token";
        public static final String VALIDATION_ERROR = "Error validating token";
    }
}
