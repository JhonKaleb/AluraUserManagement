package com.alura.UserManagement.service;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.course.dtos.CourseDTO;
import com.alura.UserManagement.domain.course.CourseStatus;
import com.alura.UserManagement.domain.course.dtos.CreateCourseDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.domain.user.UserRole;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.CourseRepository;
import com.alura.UserManagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CourseService {

    public static final String COURSE_CODE_RGX = "^[a-zA-Z-]{1,10}$";

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<String> create(CreateCourseDTO payload) {
        validateCourseCode(payload.code());
        checkCourseExistence(payload.code());
        User instructor = userService.getInstructor(payload.instructorUserName());

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

    public ResponseEntity<String> deactivateCourse(String courseCode) {
        var course = getByCode(courseCode);

        course.setStatus(CourseStatus.INACTIVE);
        course.setDeactivationDate(new Date());
        courseRepository.save(course);

        log.info("Course {} deactivated", courseCode);
        return ResponseEntity.ok(String.format("Course '%s' deactivated successfully", courseCode));
    }

    public Page<CourseDTO> listCourses(String statusStr, Pageable pageable) {
        if (statusStr == null) {
            return courseRepository.findAll(pageable).map(CourseDTO::fromCourse);
        }

        var status = CourseStatus.getByCd(statusStr.toUpperCase());
        if (status == null) {
            log.error("Invalid course status {}", statusStr);
            throw new ApiRequestException(ErrorMessages.Course.INVALID_STATUS);
        }

        Page<Course> courses = courseRepository.findByStatus(status, pageable);
        return courses.map(CourseDTO::fromCourse);
    }

    public Course getByCode(String courseCode) {
        var course = courseRepository.findByCode(courseCode);
        if (course == null) {
            log.error("Course {} not found", courseCode);
            throw new ApiRequestException(ErrorMessages.Course.NOT_FOUND);
        }

        return course;
    }
}
