package com.alura.UserManagement.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Convert(converter = UserRole.Converter.class)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "creation_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public User(String name, String username, String email, UserRole role) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority student = new SimpleGrantedAuthority(UserRole.STUDENT.getAuthority());
        SimpleGrantedAuthority instructor = new SimpleGrantedAuthority(UserRole.INSTRUCTOR.getAuthority());
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority(UserRole.ADMIN.getAuthority());

        if (role.equals(UserRole.ADMIN)) return List.of(student, instructor, admin);
        if (role.equals(UserRole.INSTRUCTOR)) return List.of(student, instructor);
        return List.of(student);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}