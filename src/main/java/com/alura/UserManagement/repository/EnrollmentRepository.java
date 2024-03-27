package com.alura.UserManagement.repository;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.enrollment.Enrollment;
import com.alura.UserManagement.domain.enrollment.EnrollmentId;
import com.alura.UserManagement.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
    Enrollment findByUserAndCourse(User user, Course course);
}