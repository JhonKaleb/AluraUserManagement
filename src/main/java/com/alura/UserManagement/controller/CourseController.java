package com.alura.UserManagement.controller;

import com.alura.UserManagement.domain.course.dtos.CourseDTO;
import com.alura.UserManagement.domain.course.CourseStatus;
import com.alura.UserManagement.domain.course.dtos.CreateCourseDTO;
import com.alura.UserManagement.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/list")
    public Page<CourseDTO> listCourses(
            @RequestParam(value = "status", required = false) String status,
            @PageableDefault(size = 5) Pageable pageable
    ) {
        return courseService.listCourses(status, pageable);
    }
}
