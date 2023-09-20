CREATE DATABASE `books`;
USE books;
CREATE TABLE `book` ( `id` INT NOT NULL AUTO_INCREMENT, `title` VARCHAR(255), `price` INT, PRIMARY KEY (`id`));
INSERT INTO book (title, price) VALUES ('Alice', 30);

