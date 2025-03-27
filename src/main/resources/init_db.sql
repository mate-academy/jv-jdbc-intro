DROP TABLE IF EXISTS books;

CREATE TABLE books (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       price DECIMAL(10,2) NOT NULL
);

INSERT INTO books (title, price) VALUES
    ('Harry Potter and the Philosopher\'s Stone', 19.99),
('The Hobbit', 14.50),
('1984', 10.00);
