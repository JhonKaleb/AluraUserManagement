package com.alura.UserManagement.repository;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.rating.Rating;
import com.alura.UserManagement.domain.report.NpsValuesFormulaProjection;
import com.alura.UserManagement.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Rating findByUserAndCourse(User user, Course course);
    
    @Query(value = "SELECT " +
            "c.name AS courseName, " +
            "r.course_code AS courseCode, " +
            "COUNT(r.id) AS totalResponses, " +
            "SUM(CASE WHEN r.rating BETWEEN 9 AND 10 THEN 1 ELSE 0 END) AS promoters, " +
            "SUM(CASE WHEN r.rating BETWEEN 0 AND 6 THEN 1 ELSE 0 END) AS detractors " +
            "FROM " +
            "ratings r " +
            "JOIN " +
            "courses c ON r.course_code = c.code " +
            "GROUP BY " +
            "r.course_code " +
            "HAVING " +
            "COUNT(r.id) > 4", nativeQuery = true)
    List<NpsValuesFormulaProjection> getValuesForNPSFormula();
}
