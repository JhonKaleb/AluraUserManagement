package com.alura.UserManagement.exception;

import com.alura.UserManagement.domain.course.CourseStatus;

import java.util.Arrays;

public class ErrorMessages {

    public static class General {
        public static final String INTERNAL_SERVER_ERROR = "Internal server error";
        public static final String INVALID_REQUEST = "Invalid request";
        public static final String ACCESS_DENIED = "Unauthorized due role not allowed";
        public static final String ENDPOINT_NOT_FOUND = "Endpoint not found";
    }

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
        public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
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

    public static class Enrollment {
        public static final String ALREADY_EXISTS = "User is already enrolled in the course";
        public static final String COURSE_INACTIVE = "Course is inactive";
    }

    public static class Rating {
        public static final String USER_NOT_ENROLLED = "User is not enrolled in the course";
        public static final String INVALID_RATING = "Invalid rating, valid values are between 1 and 10";
        public static final String ALREADY_RATED = "User already rated the course";
    }
}
