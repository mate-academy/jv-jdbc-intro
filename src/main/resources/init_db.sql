CREATE DATABASE booksHW;

USE booksHW;

CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price INT,
    PRIMARY KEY (id)
);

INSERT INTO books (title, price) VALUES
       ('Smeshariki', 20),
       ('Fiksiki', 15),
       ('Jurassic World', 10),
       ('Need for Speed 7', 17);