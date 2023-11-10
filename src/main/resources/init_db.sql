CREATE DATABASE IF NOT EXISTS books;
USE books;
CREATE TABLE `books` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255),
    `price` LONG,
    PRIMARY KEY (`id`)
);