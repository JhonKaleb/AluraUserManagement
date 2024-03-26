package com.alura.UserManagement.controller;

import com.alura.UserManagement.domain.enrollment.dtos.CreateEnrollmentDTO;
import com.alura.UserManagement.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid CreateEnrollmentDTO payload) {
        return enrollmentService.create(payload);
    }

}
