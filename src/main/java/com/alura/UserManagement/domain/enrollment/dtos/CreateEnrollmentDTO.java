package com.alura.UserManagement.domain.enrollment.dtos;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.enrollment.Enrollment;
import com.alura.UserManagement.domain.user.User;

public record CreateEnrollmentDTO(String courseCode) {

    public static Enrollment toEnrollment(User username, Course courseCode) {
        return new Enrollment(username, courseCode);
    }
}