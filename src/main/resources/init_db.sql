CREATE DATABASE book_schema;
USE book_schema;
CREATE TABLE books (`id` BIGINT NOT NULL AUTO_INCREMENT, `title` VARCHAR(255), `price` DECIMAL, PRIMARY KEY (`id`));
