package com.alura.UserManagement.repository;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.course.CourseStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByCode(String code);
    Page<Course> findByStatus(CourseStatus status, Pageable pageable);
}
