CREATE TABLE books(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR (255),
    price DECIMAL (10, 2)
);

USE test;

INSERT INTO books (title, price) VALUES ('Book One', 10.00);
INSERT INTO books (title, price) VALUES ('Book Two', 15.40);
INSERT INTO books (title, price) VALUES ('Book Three', 25.00);
INSERT INTO books (title, price) VALUES ('Book Four', 267.00);
INSERT INTO books (title, price) VALUES ('Book Five', 159.50);
INSERT INTO books (title, price) VALUES ('Book Six', 2444.99);