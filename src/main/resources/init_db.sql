CREATE DATABASE `books_schema`;
USE books_schema;
CREATE TABLE `books` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255),
    `price` INT,
    PRIMARY KEY (`id`)
);