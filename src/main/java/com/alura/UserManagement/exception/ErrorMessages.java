package com.alura.UserManagement.exception;

public class ErrorMessages {
    public static class User {
        public static final String ALREADY_EXISTS   = "User with username provided already exists";
        public static final String INVALID_PASSWORD = "Password does not meet the requirements" +
                "\n- At least 8 characters" +
                "\n- At least one uppercase letter" +
                "\n- At least one lowercase letter" +
                "\n- At least one number";
        public static final String INVALID_EMAIL    = "Email does not meet the requirements";
    }
}
