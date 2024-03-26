CREATE TABLE courses (
    `code` VARCHAR(10) NOT NULL PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `instructor_id` INT NOT NULL,
    `description` TEXT,
    `status` ENUM('ATIVO', 'INATIVO') DEFAULT 'ATIVO' NOT NULL,
    `creation_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `deactivation_date` TIMESTAMP DEFAULT NULL,
    CONSTRAINT FK_Course_Instructor FOREIGN KEY (instructor_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
