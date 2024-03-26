package com.alura.UserManagement.domain.course.dtos;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.user.User;

import java.io.Serializable;

public record CreateCourseDTO(
        String name,
        String code,
        String instructorUserName,
        String description
) implements Serializable {

    public Course toCourse(User instructor) {
        return new Course(code, name, instructor, description);
    }

}