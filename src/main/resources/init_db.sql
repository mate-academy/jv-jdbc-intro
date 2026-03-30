CREATE DATABASE library;
USE library;
CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO books (title, price) VALUES
                                     ('Clean Code', 29.99),
                                     ('Effective Java', 45.50),
                                     ('Design Patterns', 39.90);