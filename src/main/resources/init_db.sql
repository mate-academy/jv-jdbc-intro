DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO books (title, price) VALUES
('The Hitchhiker''s Guide to the Galaxy', 12.99),
('1984', 9.50),
('Pride and Prejudice', 7.25);

SELECT * FROM books;