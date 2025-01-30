CREATE DATABASE jdbc-intro
USE jdbc-intro
CREATE TABLE books (
  id INT IS NOT NULL AUTO_INCREMENT,
  title VARCHAR(255),
  price DECIMAL(10, 2)
  PRIMARY KEY (`id`)
);