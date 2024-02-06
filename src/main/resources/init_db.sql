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