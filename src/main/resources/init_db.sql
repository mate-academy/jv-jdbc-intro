CREATE DATABASE hw_books;
USE hw_books;

CREATE TABLE books (
                       id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR (255) NOT NULL ,
                       price DECIMAL (16)
);

INSERT INTO books (title, price) VALUES ('The Mythical Man-Month', 260);
INSERT INTO books (title, price) VALUES ('The Art of Computer Programming', 500);
INSERT INTO books (title, price) VALUES ('The Dogs of War', 180);