DROP database IF EXISTS booksdb;

CREATE database booksdb;

USE booksdb;

CREATE TABLE books (
	id BIGINT PRIMARY KEY auto_increment,
	title VARCHAR(255),
	price DECIMAL(4, 2)
);
