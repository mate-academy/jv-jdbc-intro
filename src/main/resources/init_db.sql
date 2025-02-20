CREATE DATABASE IF NOT EXISTS booksstore;
USE booksstore;

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
);

INSERT INTO books (title, author, price, published_year) VALUES
('The Great Gatsby', 10.99),
('1984', 8.99),
('To Kill a Mockingbird', 12.50);