CREATE TABLE books(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR (255),
    price DECIMAL (10, 2)
);

USE test;

INSERT INTO books (title, price) VALUES ('White Fang', 20.00);
INSERT INTO books (title, price) VALUES ('Sherlock Holmes', 28.40);
INSERT INTO books (title, price) VALUES ('The Lord of Flies', 21.00);
INSERT INTO books (title, price) VALUES ('Ten little Niggers', 23.00);
INSERT INTO books (title, price) VALUES ('The call of the Wild', 15.50);
INSERT INTO books (title, price) VALUES ('Treasure Island', 24.99);