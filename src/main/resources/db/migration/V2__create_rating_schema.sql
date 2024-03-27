CREATE TABLE `ratings` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `user_id` INT NOT NULL,
  `course_code` VARCHAR(10) NOT NULL,
  `rating` INT NOT NULL CHECK (rating BETWEEN 1 AND 10),
  `comment` TEXT,
  `rating_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT FK_Rating_User FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
  CONSTRAINT FK_Rating_Course FOREIGN KEY (`course_code`) REFERENCES `courses`(`code`),
  UNIQUE (`user_id`, `course_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;