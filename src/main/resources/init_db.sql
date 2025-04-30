-- Create database if it does not exist
CREATE DATABASE IF NOT EXISTS book_test_mate;

-- Grant all privileges to root user
GRANT ALL PRIVILEGES ON book_test_mate.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

-- Switch to the new database
USE book_test_mate;

-- Create table books
CREATE TABLE books
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);