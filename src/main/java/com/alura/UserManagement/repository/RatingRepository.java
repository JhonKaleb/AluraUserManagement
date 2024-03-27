package com.alura.UserManagement.repository;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.rating.Rating;
import com.alura.UserManagement.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Rating findByUserAndCourse(User user, Course course);
}
