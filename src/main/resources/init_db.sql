CREATE SCHEMA IF NOT EXISTS `book_db`;

USE book_db;

CREATE TABLE IF NOT EXISTS `books` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL ,
    `price` NUMERIC(10, 2) NOT NULL,
     PRIMARY KEY (`id`)
);

INSERT INTO books (title, price) VALUES ('Atomic Habits', 736.0);
INSERT INTO books (title, price) VALUES ('Rich Dad Poor Dad', 545.0);
INSERT INTO books (title, price) VALUES ('Intelligent Investor', 1025.0);
