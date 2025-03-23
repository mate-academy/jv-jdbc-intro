CREATE DATABASE books_db;
USE books_db;
CREATE TABLE books (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(100),
    `price` DOUBLE,
    PRIMARY KEY (`id`)
);
