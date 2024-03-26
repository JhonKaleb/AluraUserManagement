package com.alura.UserManagement.exception;

import com.alura.UserManagement.domain.course.CourseStatus;

import java.util.Arrays;

public class ErrorMessages {
    public static class User {
        public static final String ALREADY_EXISTS = "User with username provided already exists";
        public static final String INVALID_PASSWORD = "Password does not meet the requirements" +
                "- At least 8 characters" +
                "- At least one uppercase letter" +
                "- At least one lowercase letter" +
                "- At least one number";
        public static final String INVALID_EMAIL = "Email does not meet the requirements";
        public static final String NOT_FOUND = "User not found";
        public static final String INSTRUCTOR_NOT_FOUND = "Instructor not found";
    }

    public static class Token {
        public static final String CREATION_ERROR = "Error creating token";
        public static final String VALIDATION_ERROR = "Error validating token";
    }

    public static class Course {
        public static final String INVALID_CODE = "Invalid course code. " +
                "The course code must be textual with a maximum of 10 chars, without spaces, " +
                "numbers or special characters, but can be separated by -. " +
                "Example: spring-adv";
        public static final String ALREADY_EXISTS = "Course with code provided already exists";
        public static final String NOT_FOUND = "Course not found";
        public static final String INVALID_STATUS = "Invalid course status, valid values are: "
                + Arrays.toString(Arrays.stream(CourseStatus.values()).map(CourseStatus::getCode).toArray());
    }
}
