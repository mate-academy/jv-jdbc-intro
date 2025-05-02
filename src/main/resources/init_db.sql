CREATE DATABASE bookstore;
USE bookstore;
CREATE TABLE books
(
    id    BIGINT  AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    price DECIMAL(10, 2)
);
