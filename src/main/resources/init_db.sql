SHOW DATABASES;

CREATE DATABASE book_store;

USE book_store;

CREATE TABLE books
(
    id     BIGINT          NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    price  DECIMAL(10, 2),
    PRIMARY KEY (id)
);
