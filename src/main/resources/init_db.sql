CREATE DATABASE book;
USE book;
CREATE TABLE books (
    -> `id` BIGINT NOT NULL AUTO_INCREMENT,
    -> `title` VARCHAR(255),
    -> `price` DEC(4,2) NOT NULL,
    -> PRIMARY KEY (`id`)
    -> );