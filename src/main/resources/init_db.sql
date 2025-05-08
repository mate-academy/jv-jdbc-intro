CREATE DATABASE shop;
use shop;
CREATE TABLE books
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    title  varchar(255),
    price INT,
    PRIMARY KEY (id)
);

DELETE FROM books WHERE id = 8;