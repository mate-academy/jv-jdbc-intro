-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS bookstore;

-- Switch to the created database
USE bookstore;

-- Create the table for the Book model
CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    );
