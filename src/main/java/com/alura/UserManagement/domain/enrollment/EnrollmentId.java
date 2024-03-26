package com.alura.UserManagement.domain.enrollment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class EnrollmentId implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "course_code")
    private String courseCode;

}
