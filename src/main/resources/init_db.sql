CREATE DATABASE books_db;
USE books_db;
CREATE TABLE books (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100),
    `price` DECIMAL,
    PRIMARY KEY (`id`)
);
