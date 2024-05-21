CREATE DATABASE booksDb;

USE booksDb;

CREATE TABLE books
(
    id    BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price DECIMAL(10, 2),
    PRIMARY KEY (id)
);

INSERT INTO books (title, price) VALUES
        ('The Catcher in the Rye', 9.99),
       ('To Kill a Mockingbird', 14.99),
       ('1984', 12.50),
       ('Pride and Prejudice', 7.95),
       ('The Great Gatsby', 10.99);
