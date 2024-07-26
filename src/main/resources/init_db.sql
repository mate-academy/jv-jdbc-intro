CREATE DATABASE book_repository;

USE book_repository;

CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(128),
    price DECIMAL(10, 2)
);