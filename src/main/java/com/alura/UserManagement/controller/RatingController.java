package com.alura.UserManagement.controller;

import com.alura.UserManagement.domain.enrollment.dtos.CreateEnrollmentDTO;
import com.alura.UserManagement.domain.rating.dtos.CreateRatingDTO;
import com.alura.UserManagement.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid CreateRatingDTO payload) {
        return ratingService.create(payload);
    }
}
