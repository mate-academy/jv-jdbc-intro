CREATE DATABASE `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
CREATE TABLE `library`.`books` (
                                   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                   `title` VARCHAR(45) NOT NULL,
                                   `price` DECIMAL NOT NULL,
                                   `is_deleted` TINYINT(1) NULL DEFAULT 0,
                                   PRIMARY KEY (`id`)
                               );
