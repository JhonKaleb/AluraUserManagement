package com.alura.UserManagement.repository;

import com.alura.UserManagement.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByCode(String code);
}
