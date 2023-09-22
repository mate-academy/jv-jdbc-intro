CREATE DATABASE list_book;
USE list_book;
CREATE TABLE books
(
    id    INT            NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255)   NOT NULL,
    price DECIMAL(10, 2)
);