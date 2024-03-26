package com.alura.UserManagement.domain.enrollment;

import com.alura.UserManagement.domain.course.Course;
import com.alura.UserManagement.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "enrollments")
@Table(name = "enrollments")
public class Enrollment {

    @EmbeddedId
    private EnrollmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseCode")
    @JoinColumn(name = "course_code")
    private Course course;

    @Column(name = "enrollment_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enrollmentDate = new Date();

    public Enrollment(User user, Course course) {
        this.user = user;
        this.course = course;
        this.id = new EnrollmentId(user.getId(), course.getCode());
    }

}