CREATE DATABASE IF NOT EXISTS BookStore DEFAULT CHARACTER SET utf8;

USE BookStore;

CREATE TABLE Books (
    id BIGINT AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
    );

INSERT INTO Books (title, price) VALUES ('The Great Gatsby', 10.99);
INSERT INTO Books (title, price) VALUES ('1984', 8.99);
INSERT INTO Books (title, price) VALUES ('To Kill a Mockingbird', 12.50);
INSERT INTO Books (title, price) VALUES ('Pride and Prejudice', 9.75);
INSERT INTO Books (title, price) VALUES ('The Catcher in the Rye', 11.20);