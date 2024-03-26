CREATE TABLE enrollments (
  `user_id` INT NOT NULL,
  `course_code` VARCHAR(10) NOT NULL,
  `enrollment_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`, `course_code`),
  CONSTRAINT FK_Enrollment_User FOREIGN KEY (`user_id`) REFERENCES users(`id`),
  CONSTRAINT FK_Enrollment_Course FOREIGN KEY (`course_code`) REFERENCES courses(`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
