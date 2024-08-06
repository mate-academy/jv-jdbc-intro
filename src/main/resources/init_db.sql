CREATE SCHEMA IF NOT EXISTS bookstore;

USE bookstore;

CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    /* author VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    published_date DATE */
    );