package com.alura.UserManagement.controller;

import com.alura.UserManagement.domain.course.CreateCourseDTO;
import com.alura.UserManagement.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid CreateCourseDTO payload) {
        return courseService.create(payload);
    }

    @PatchMapping("/{courseCode}/deactivate")
    public ResponseEntity<String> deactivate(@PathVariable String courseCode) {
        return courseService.deactivateCourse(courseCode);
    }

}
