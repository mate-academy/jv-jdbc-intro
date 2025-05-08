CREATE DATABASE IF NOT EXISTS book_storage;

USE book_storage;

CREATE TABLE IF NOT EXISTS books(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(255) NOT NULL,
price DECIMAL NOT NULL
);

INSERT INTO books(title,price) VALUES
        ('Effective Java', 45.99),
        ('Thinking in Java', 48.95),
        ('Head First Java', 40.75),
        ('Java: A Beginners Guide', 30.99),
        ('Java SE8 for the Really Impatient', 37.99),
        ('Java Performance: The Definitive Guide', 50.00),
        ('Clean Code: A Handbook of Agile Software Craftsmanship', 44.50),
        ('Spring in Action', 39.99),
        ('Java Concurrency in Practice', 42.80);

SELECT * FROM books;

