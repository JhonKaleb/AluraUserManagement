package com.alura.UserManagement.domain.course.dtos;

import com.alura.UserManagement.domain.course.Course;

import java.util.Date;

public record CourseDTO(
        String code,
        String name,
        String instructorName,
        String description,
        String status,
        Date creationDate,
        Date deactivationDate
) {
    public static CourseDTO fromCourse(Course course) {
        return new CourseDTO(
                course.getCode(),
                course.getName(),
                course.getInstructor().getName(),
                course.getDescription(),
                course.getStatus().getCode(),
                course.getCreationDate(),
                course.getDeactivationDate()
        );
    }
}