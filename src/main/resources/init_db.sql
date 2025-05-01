CREATE SCHEMA IF NOT EXISTS book_store;

USE book_store;

CREATE TABLE IF NOT EXISTS books (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    );
