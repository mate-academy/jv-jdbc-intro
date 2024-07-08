CREATE DATABASE `my_db`;
USE my_db;
CREATE TABLE books (
    id BIGINT PRIMARY KEY,
    title VARCHAR(50),
    price DECIMAL(10,2)
    );