CREATE DATABASE `books_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `books_db`.`books` (
                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                    `title` VARCHAR(255) NOT NULL,
                                    `price` DECIMAL NOT NULL DEFAULT 0,
                                    `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                    PRIMARY KEY (`id`));