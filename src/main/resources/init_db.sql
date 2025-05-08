CREATE DATABASE db_example;
USE db_example;
CREATE TABLE books (id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    title VARCHAR(255),
                    price DECIMAL);