package com.alura.UserManagement.domain.course;

import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.domain.user.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@EqualsAndHashCode(of = "code")
@Getter
@Setter
@Entity(name = "courses")
@Table(name = "courses")
public class Course {
    @Id
    @Column(name = "code", nullable = false, length = 10, unique = true)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id", nullable = false)
    private User instructor;

    @Column(name = "description")
    private String description;

    @Convert(converter = CourseStatus.Converter.class)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'")
    private CourseStatus status = CourseStatus.ACTIVE;

    @Column(name = "creation_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "deactivation_date", columnDefinition = "TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deactivationDate;

    public Course(String code, String name, User instructor, String description) {
        this.code = code;
        this.name = name;
        this.instructor = instructor;
        this.description = description;
    }
}
