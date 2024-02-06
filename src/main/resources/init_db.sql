--Create a schema for the task;
CREATE DATABASE jdbc_intro;
--Use this schema;
USE jdbc_intro;
--Create "books" table in the schema;
CREATE TABLE books(
id BIGINT AUTO_INCREMENT,
title VARCHAR(255) NOT NULL,
price INT,
PRIMARY KEY (id));
--Check if everything looks as expected;
SELECT *
FROM books;
--To be able to work with java big decimal, alter table column "price".
--It's now able to show prices up to 19 999 999,99;
ALTER TABLE books
MODIFY COLUMN price DECIMAL(10, 2);