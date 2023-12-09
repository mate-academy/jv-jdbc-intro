CREATE DATABASE test;
USE test;
CREATE TABLE book
(
    id  INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200),
    price  DECIMAL (38, 10),
    PRIMARY KEY (id)
);