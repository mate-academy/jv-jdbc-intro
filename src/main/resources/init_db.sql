CREATE DATABASE library;

CREATE TABLE library.books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(32),
    price DECIMAL(9, 2),
    PRIMARY KEY (id)
);