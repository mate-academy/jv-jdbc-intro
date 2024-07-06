CREATE DATABASE book_db;

USE book_db;

CREATE TABLE book
(
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(50),
  price decimal(8,2),
  PRIMARY KEY (id)
);

INSERT INTO book(title, price)
    VALUES  ('John Rouel Tolkien Lord of the Rings', 980.05),
            ('Kleiv Luis Narnia', 750.45),
            ('John Rouel Tolkien Silmarilion', 230.25),
            ('Taras Shevchenko Kobzar', 305.37),
            ('King David The psalms', 100.45),
            ('John Kalvin Reformation of theology', 345.25),
            ('Ivan Kotlyarevsky Eneida', 267.34);


