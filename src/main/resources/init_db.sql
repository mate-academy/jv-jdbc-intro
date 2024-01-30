CREATE DATABASE books;
USE books;
CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price DECIMAL(10, 2),
    PRIMARY KEY (id)
);
SHOW TABLES;
SET GLOBAL time_zone = '+3:00';