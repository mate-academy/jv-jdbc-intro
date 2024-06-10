-- Create database
CREATE DATABASE `database`;
-- Create the users table
CREATE TABLE `books`
(
    `id`    INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255),
    `price` DECIMAL(10, 2),
    PRIMARY KEY (id)
);
-- Insert data
INSERT INTO books (title, price)
VALUES (?, ?);
