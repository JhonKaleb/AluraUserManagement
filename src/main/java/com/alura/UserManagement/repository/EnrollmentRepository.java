package com.alura.UserManagement.repository;

import com.alura.UserManagement.domain.enrollment.Enrollment;
import com.alura.UserManagement.domain.enrollment.EnrollmentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentId> {
}