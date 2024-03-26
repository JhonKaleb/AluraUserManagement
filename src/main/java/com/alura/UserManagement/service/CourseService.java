package com.alura.UserManagement.service;

import com.alura.UserManagement.domain.course.CreateCourseDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.domain.user.UserRole;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.CourseRepository;
import com.alura.UserManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CourseService {

    public static final String COURSE_CODE_RGX = "^[a-zA-Z-]{1,10}$";

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> create(CreateCourseDTO payload) {
        validateCourseCode(payload.code());
        checkCourseExistence(payload.code());
        User instructor = getInstructor(payload.instructorUserName());

        var course = payload.toCourse(instructor);
        courseRepository.save(course);

        log.info("Course created: {}", course.getCode());
        return ResponseEntity.ok(String.format("Course '%s' created successfully", course.getCode()));
    }

    private void validateCourseCode(String code) {
        if (!code.matches(COURSE_CODE_RGX)) {
            log.error("Invalid course code {}", code);
            throw new ApiRequestException(ErrorMessages.Course.INVALID_CODE);
        }
    }

    private void checkCourseExistence(String code) {
        if (courseRepository.findByCode(code) != null) {
            log.error("Course code {} already exists", code);
            throw new ApiRequestException(ErrorMessages.Course.ALREADY_EXISTS);
        }
    }

    private User getInstructor(String username) {
        var instructor = userRepository.findByUsernameAndRole(username, UserRole.INSTRUCTOR);
        if (instructor == null) {
            log.error("Instructor not found");
            throw new ApiRequestException(ErrorMessages.User.INSTRUCTOR_NOT_FOUND);
        }
        return instructor;
    }
}
