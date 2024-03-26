package com.alura.UserManagement;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.course.CourseStatus;
import com.alura.UserManagement.domain.enrollment.Enrollment;
import com.alura.UserManagement.domain.enrollment.dtos.CreateEnrollmentDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.EnrollmentRepository;
import com.alura.UserManagement.service.CourseService;
import com.alura.UserManagement.service.EnrollmentService;
import com.alura.UserManagement.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EnrollmentTests {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private UserService userService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private EnrollmentService enrollmentService;

    private User mockUser;
    private Course mockCourse;

    @BeforeEach
    public void setup() {
        mockUser = new User();
        mockUser.setUsername("testuser");

        mockCourse = new Course();
        mockCourse.setCode("COURSE");
        mockCourse.setStatus(CourseStatus.ACTIVE);
    }

    // Successful enrollment creation
    @Test
    public void testCreateEnrollmentSuccessfully() {
        var payload = new CreateEnrollmentDTO("COURSE");

        Mockito.when(courseService.getByCode("COURSE")).thenReturn(mockCourse);
        Mockito.when(userService.getAuthenticated()).thenReturn(mockUser);
        Mockito.when(enrollmentRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        var response = enrollmentService.create(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Attempting to enroll in an inactive course
    @Test
    public void testEnrollmentInInactiveCourse() {
        mockCourse.setStatus(CourseStatus.INACTIVE);
        CreateEnrollmentDTO payload = new CreateEnrollmentDTO("COURSE");

        Mockito.when(courseService.getByCode("COURSE")).thenReturn(mockCourse);

        try {
            enrollmentService.create(payload);
            Assertions.fail("Expected ApiRequestException to be thrown due to inactive course.");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.Enrollment.COURSE_INACTIVE, e.getMessage());
        }
    }

    // Attempting to create an enrollment that already exists
    @Test
    public void testCreateExistingEnrollment() {
        CreateEnrollmentDTO payload = new CreateEnrollmentDTO("COURSE");
        Enrollment existingEnrollment = new Enrollment();

        Mockito.when(courseService.getByCode("COURSE")).thenReturn(mockCourse);
        Mockito.when(userService.getAuthenticated()).thenReturn(mockUser);
        Mockito.when(enrollmentRepository.findById(Mockito.any())).thenReturn(Optional.of(existingEnrollment));

        try {
            enrollmentService.create(payload);
            Assertions.fail("Expected ApiRequestException to be thrown due to existing enrollment.");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.Enrollment.ALREADY_EXISTS, e.getMessage());
        }
    }
}
