CREATE DATABASE library;

USE library;

CREATE TABLE books (
        id BIGINT NOT NULL AUTO_INCREMENT,
        title TEXT,
        price DECIMAL,
        PRIMARY KEY (id)
);
