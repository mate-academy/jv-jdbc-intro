SHOW DATABASES;

CREATE DATABASE book_store;

USE book_store;

CREATE TABLE books
(
    id     INT          NOT NULL AUTO_INCREMENT,
    tittle VARCHAR(255) NOT NULL,
    price  DECIMAL(10, 2),
    PRIMARY KEY (id)
);

ALTER TABLE books
CHANGE tittle title VARCHAR(255) NOT NULL;