package com.alura.UserManagement.service;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.course.CourseStatus;
import com.alura.UserManagement.domain.enrollment.Enrollment;
import com.alura.UserManagement.domain.enrollment.dtos.CreateEnrollmentDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.EnrollmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    public ResponseEntity<String> create(CreateEnrollmentDTO payload) {
        var course = courseService.getByCode(payload.courseCode());
        checkCourseActive(course);

        var user = userService.getAuthenticated();
        var enrollment = CreateEnrollmentDTO.toEnrollment(user, course);
        checkEnrollmentExist(enrollment);

        enrollmentRepository.save(enrollment);
        return ResponseEntity.ok(
                String.format(
                        "Enrollment created successfully for user '%s' in course '%s'",
                        user.getUsername(),
                        course.getCode()
                )
        );
    }

    private void checkEnrollmentExist(Enrollment enrollment) {
        enrollmentRepository.findById(enrollment.getId())
                .ifPresent(e -> {
                    log.error("Enrollment already exists: {}", e.getId());
                    throw new ApiRequestException(ErrorMessages.Enrollment.ALREADY_EXISTS);
                });
    }

    private static void checkCourseActive(Course course) {
        if (course.getStatus().equals(CourseStatus.INACTIVE)) {
            log.error("Course {} is inactive", course.getCode());
            throw new ApiRequestException(ErrorMessages.Enrollment.COURSE_INACTIVE);
        }
    }

    public Enrollment getEnrollment(User user, Course course) {
        return enrollmentRepository.findByUserAndCourse(user, course);
    }
}
