SHOW DATABASES;

CREATE database books_db;

USE books_db;

CREATE TABLE books (
      id BIGINT NOT NULL AUTO_INCREMENT,
      title VARCHAR(255),
      price INT,
      PRIMARY KEY (id)
);

SHOW TABLES;
