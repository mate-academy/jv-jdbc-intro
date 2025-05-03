CREATE DATABASE book_db;
USE book_db;
CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50),
    price BIGINT
);
