package com.alura.UserManagement.service;

import com.alura.UserManagement.domain.rating.dtos.CreateRatingDTO;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RatingService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private RatingRepository ratingRepository;

    public ResponseEntity<String> create(CreateRatingDTO payload) {
        log.info("Creating rating for course {}", payload.courseCode());

        validateRatingValue(payload.rating());
        var course = courseService.getByCode(payload.courseCode());
        var user = userService.getAuthenticated();

        //verify if user alredy rated the course
        var rating = ratingRepository.findByUserAndCourse(user, course);
        if (rating != null) {
            log.error("User {} already rated course {}", user.getUsername(), course.getCode());
            throw new ApiRequestException(ErrorMessages.Rating.ALREADY_RATED);
        }

        var enrollment = enrollmentService.getEnrollment(user, course);
        if (enrollment == null) {
            log.error("User {} is not enrolled in course {}", user.getUsername(), course.getCode());
            throw new ApiRequestException(ErrorMessages.Rating.USER_NOT_ENROLLED);
        }

        rating = CreateRatingDTO.toRating(enrollment, payload);
        ratingRepository.save(rating);
        return ResponseEntity.ok("Rating created successfully");
    }

    private static void validateRatingValue(Integer rate) {
        if(rate < 1 || rate > 10) {
            log.error("Invalid rating value: {}", rate);
            throw new ApiRequestException(ErrorMessages.Rating.INVALID_RATING);
        }
    }
}
