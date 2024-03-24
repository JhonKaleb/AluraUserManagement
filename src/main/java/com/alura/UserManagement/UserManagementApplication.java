package com.alura.UserManagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@ComponentScan({
		"com.alura.UserManagement.config",
		"com.alura.UserManagement.service",
		"com.alura.UserManagement.repository",
		"com.alura.UserManagement.controller",
		"com.alura.UserManagement.exception",
		"com.alura.UserManagement.filter",
})
@EntityScan(basePackages = {"com.alura.UserManagement.domain"})
@EnableJpaRepositories({"com.alura.UserManagement.repository"})
@EnableTransactionManagement
@EnableConfigurationProperties
@SpringBootApplication
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
