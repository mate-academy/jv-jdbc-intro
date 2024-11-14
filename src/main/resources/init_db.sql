CREATE TABLE books(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR (255),
    price DECIMAL (10, 2)
);

USE test;

INSERT INTO books (title, price) VALUES ('Test1', 10.00);
INSERT INTO books (title, price) VALUES ('Test2', 15.40);
INSERT INTO books (title, price) VALUES ('Test3', 25.00);
INSERT INTO books (title, price) VALUES ('Test4', 267.00);
INSERT INTO books (title, price) VALUES ('Test5', 159.50);
INSERT INTO books (title, price) VALUES ('Test6', 2444.99);