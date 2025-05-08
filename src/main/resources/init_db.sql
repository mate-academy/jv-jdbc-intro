CREATE SCHEMA book_schema;

USE book_schema;

CREATE TABLE books
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255)   NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO books (title, price)
VALUE ('test book', 243.23);
