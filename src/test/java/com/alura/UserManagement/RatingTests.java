package com.alura.UserManagement;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.enrollment.Enrollment;
import com.alura.UserManagement.domain.rating.Rating;
import com.alura.UserManagement.domain.rating.dtos.CreateRatingDTO;
import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.RatingRepository;
import com.alura.UserManagement.service.CourseService;
import com.alura.UserManagement.service.EnrollmentService;
import com.alura.UserManagement.service.RatingService;
import com.alura.UserManagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RatingTests {
    @Mock
    private CourseService courseService;

    @Mock
    private UserService userService;

    @Mock
    private EnrollmentService enrollmentService;

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    private User user;
    private Course course;
    private Enrollment enrollment;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setUsername("testUser");

        course = new Course();
        course.setCode("COURSE");

        enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
    }

    // Successful rating creation
    @Test
    public void testCreateRatingSuccessfully() {
        CreateRatingDTO payload = new CreateRatingDTO("COURSE", "Great course", 9);

        Mockito.when(courseService.getByCode(payload.courseCode())).thenReturn(course);
        Mockito.when(userService.getAuthenticated()).thenReturn(user);
        Mockito.when(ratingRepository.findByUserAndCourse(user, course)).thenReturn(null);
        Mockito.when(enrollmentService.getEnrollment(user, course)).thenReturn(enrollment);

        ResponseEntity<String> response = ratingService.create(payload);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // Attempting to rate a course without being enrolled
    @Test
    public void testRatingWithoutEnrollment() {
        CreateRatingDTO payload = new CreateRatingDTO("COURSE", "Great course", 9);

        Mockito.when(courseService.getByCode(payload.courseCode())).thenReturn(course);
        Mockito.when(userService.getAuthenticated()).thenReturn(user);
        Mockito.when(enrollmentService.getEnrollment(user, course)).thenReturn(null);

        Exception exception = assertThrows(ApiRequestException.class, () -> ratingService.create(payload));

        assertEquals(ErrorMessages.Rating.USER_NOT_ENROLLED, exception.getMessage());
    }

    // Attempting to rate a course more than once
    @Test
    public void testRatingACourseMoreThanOnce() {
        CreateRatingDTO payload = new CreateRatingDTO("COURSE", "Great course", 9);
        Rating existingRating = new Rating(); // Assume a constructor or setters

        Mockito.when(courseService.getByCode(payload.courseCode())).thenReturn(course);
        Mockito.when(userService.getAuthenticated()).thenReturn(user);
        Mockito.when(ratingRepository.findByUserAndCourse(user, course)).thenReturn(existingRating);

        Exception exception = assertThrows(ApiRequestException.class, () -> ratingService.create(payload));

        assertEquals(ErrorMessages.Rating.ALREADY_RATED, exception.getMessage());
    }

    // Providing an invalid rating value
    @Test
    public void testInvalidRatingValue() {
        CreateRatingDTO payload = new CreateRatingDTO("COURSE", "Great course", 11); // Invalid rating

        Exception exception = assertThrows(ApiRequestException.class, () -> ratingService.create(payload));

        assertEquals(ErrorMessages.Rating.INVALID_RATING, exception.getMessage());
    }
}
