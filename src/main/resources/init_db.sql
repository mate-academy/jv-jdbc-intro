CREATE SCHEMA IF NOT EXISTS `books_storage`;

USE `books_storage`;

CREATE TABLE IF NOT EXISTS books (
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   title VARCHAR(255),
   price INT
);
