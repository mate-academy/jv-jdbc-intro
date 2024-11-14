CREATE TABLE books(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR (255),
    price DECIMAL (10, 2)
);

USE test;

INSERT INTO books (title, price) VALUES ('book3', 10.00);
INSERT INTO books (title, price) VALUES ('book4', 15.40);
INSERT INTO books (title, price) VALUES ('book5', 25.00);
INSERT INTO books (title, price) VALUES ('book67', 267.00);
INSERT INTO books (title, price) VALUES ('book6', 159.50);
INSERT INTO books (title, price) VALUES ('book8', 2444.99);