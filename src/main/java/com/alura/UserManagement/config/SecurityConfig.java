package com.alura.UserManagement.config;

import com.alura.UserManagement.domain.user.UserRole;
import com.alura.UserManagement.exception.ApiRequestException;
import com.alura.UserManagement.exception.ErrorMessages;
import com.alura.UserManagement.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/user/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user/{username}").hasRole(UserRole.ADMIN.getCode())
                        .requestMatchers(HttpMethod.POST, "/course").hasRole(UserRole.ADMIN.getCode())
                        .requestMatchers(HttpMethod.PATCH, "/course/{courseCode}/deactivate").hasRole(UserRole.ADMIN.getCode())
                        .requestMatchers(HttpMethod.GET, "/course/list").hasRole(UserRole.ADMIN.getCode())
                        .requestMatchers(HttpMethod.POST, "/enrollment").authenticated()
                        .requestMatchers(HttpMethod.GET, "/reports/nps").hasRole(UserRole.ADMIN.getCode())
                ).exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"%s\"}".formatted(ErrorMessages.General.ACCESS_DENIED));
        };
    }
}
