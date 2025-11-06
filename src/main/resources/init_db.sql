CREATE SCHEMA IF NOT EXISTS `book_store` DEFAULT CHARACTER SET utf8;
USE `book_store`;

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `title` VARCHAR(255) NOT NULL,
                         `price` DECIMAL(10, 2) NOT NULL,
                         PRIMARY KEY (`id`)
);