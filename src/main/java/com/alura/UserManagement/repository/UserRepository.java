package com.alura.UserManagement.repository;

import com.alura.UserManagement.domain.user.User;
import com.alura.UserManagement.domain.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findUserDetailsByUsername(String username);
    User findByUsername(String username);
    User findByUsernameAndRole(String username, UserRole role);
}

