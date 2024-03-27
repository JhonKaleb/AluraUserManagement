package com.alura.UserManagement.domain.rating.dtos;

import com.alura.UserManagement.domain.enrollment.Enrollment;
import com.alura.UserManagement.domain.rating.Rating;

import java.io.Serializable;

public record CreateRatingDTO(
        String courseCode,
        String comment,
        Integer rating
    ) implements Serializable {

    public static Rating toRating(Enrollment enrollment, CreateRatingDTO createRatingDTO) {
        return new Rating(enrollment.getUser(), enrollment.getCourse(), createRatingDTO.rating(), createRatingDTO.comment());
    }
}
