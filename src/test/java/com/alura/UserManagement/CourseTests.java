package com.alura.UserManagement;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.course.CourseStatus;
import com.alura.UserManagement.domain.course.CreateCourseDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.domain.user.UserRole;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.CourseRepository;
import com.alura.UserManagement.repository.UserRepository;
import com.alura.UserManagement.service.CourseService;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CourseTests {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourseService courseService;


    /** ENDPOINT : POST /course */

    // Creating a new course with valid details
    @Test
    public void test_create_new_course_with_valid_details() {
        CreateCourseDTO createCourseDTO = new CreateCourseDTO(
                "Course Name",
                "CourseCode",
                "instructorUsername",
                "Course Description"
        );

        var instructor = new User();
        Mockito.when(userRepository.findByUsernameAndRole("instructorUsername", UserRole.INSTRUCTOR)).thenReturn(instructor);
        Mockito.when(courseRepository.findByCode("CourseCode")).thenReturn(null);

        var response = courseService.create(createCourseDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Attempting to create a course with an existing code
    @Test
    public void test_create_course_with_existing_code() {
        CreateCourseDTO createCourseDTO = new CreateCourseDTO(
                "Course Name",
                "ExistingCd",
                "instructorUsername",
                "Course Description"
        );

        Mockito.when(courseRepository.findByCode("ExistingCd")).thenReturn(new Course());

        try {
            courseService.create(createCourseDTO);
            Assertions.fail("Expected ApiRequestException to be thrown due to existing course code");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.Course.ALREADY_EXISTS, e.getMessage());
        }
    }

    // Attempting to create a course with an invalid course code
    @Test
    public void test_create_course_with_invalid_code() {
        CreateCourseDTO createCourseDTO = new CreateCourseDTO(
                "Course Name",
                "Invalid_Code!",
                "instructorUsername",
                "Course Description"
        );

        try {
            courseService.create(createCourseDTO);
            Assertions.fail("Expected ApiRequestException to be thrown due to invalid course code");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.Course.INVALID_CODE, e.getMessage());
        }
    }

    // Attempting to create a course with a non-existent instructor
    @Test
    public void test_create_course_with_nonexistent_instructor() {
        CreateCourseDTO createCourseDTO = new CreateCourseDTO(
                "Course Name",
                "CourseCode",
                "nonexistentInstructor",
                "Course Description"
        );

        Mockito.when(userRepository.findByUsernameAndRole("nonexistentInstructor", UserRole.INSTRUCTOR)).thenReturn(null);

        try {
            courseService.create(createCourseDTO);
            Assertions.fail("Expected ApiRequestException to be thrown due to non-existent instructor");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.User.INSTRUCTOR_NOT_FOUND, e.getMessage());
        }
    }

    /** ENDPOINT : PATCH /course/{courseCode}/deactivate */

    // Successfully deactivating an active course
    @Test
    public void test_deactivate_active_course_successfully() {
        String courseCode = "ActiveCourse";
        Course activeCourse = new Course();
        activeCourse.setCode(courseCode);
        activeCourse.setStatus(CourseStatus.ACTIVE);

        Mockito.when(courseRepository.findByCode(courseCode)).thenReturn(activeCourse);
        Mockito.doAnswer(invocation -> {
            Course course = invocation.getArgument(0);
            assertEquals(CourseStatus.INACTIVE, course.getStatus());
            return null;
        }).when(courseRepository).save(any(Course.class));

        ResponseEntity<String> response = courseService.deactivateCourse(courseCode);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Attempting to deactivate a course that does not exist
    @Test
    public void test_deactivate_nonexistent_course() {
        String nonexistentCourseCode = "NonexistentCourse";

        Mockito.when(courseRepository.findByCode(nonexistentCourseCode)).thenReturn(null);

        try {
            courseService.deactivateCourse(nonexistentCourseCode);
            Assertions.fail("Expected ApiRequestException to be thrown due to course not found");
        } catch (ApiRequestException e) {
            assertEquals(ErrorMessages.Course.NOT_FOUND, e.getMessage());
        }
    }
}
