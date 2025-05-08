CREATE SCHEMA IF NOT EXISTS jdbc_intro_trokhymchuk;

USE jdbc_intro_trokhymchuk;

CREATE TABLE IF NOT EXISTS book (
id INT AUTO_INCREMENT PRIMARY KEY,
title VARCHAR(255),
price DECIMAL(10, 2)
);

