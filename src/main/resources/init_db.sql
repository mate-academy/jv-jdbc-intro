DROP DATABASE IF EXISTS books;
CREATE DATABASE books;
USE books;
DROP TABLE IF EXISTS books;
CREATE TABLE books (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       title VARCHAR(255) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL);