CREATE SCHEMA IF NOT EXISTS `books_db` DEFAULT CHARACTER SET utf8 ;
USE `books_db` ;
CREATE TABLE IF NOT EXISTS `books_db`.`books` (
                                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                  `title` VARCHAR(255) NULL,
    `price` DECIMAL UNSIGNED NULL,
    PRIMARY KEY (`id`));