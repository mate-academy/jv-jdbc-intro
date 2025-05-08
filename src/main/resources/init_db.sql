CREATE DATABASE `book_db` DEFAULT CHARACTER SET utf8;

USE book_db;

CREATE TABLE `book`
(
    `id`    BIGINT         NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255)   NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id`)
);

