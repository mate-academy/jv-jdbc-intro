CREATE TABLE books (
id INT NOT NULL AUTO_INCREMENT,
title VARCHAR(255),
price INT,
PRIMARY KEY (id));

SELECT * FROM books;

INSERT INTO books VALUES
(1,'The Witcher', 160),
(2,'Normal People', 120);