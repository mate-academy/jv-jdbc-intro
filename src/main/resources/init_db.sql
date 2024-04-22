CREATE TABLE books (
id BIGINT NOT NULL AUTO_INCREMENT,
title VARCHAR(255),
price BIGINT,
PRIMARY KEY (id));

SELECT * FROM books;

INSERT INTO books VALUES
(1,'The Witcher', 160),
(2,'Normal People', 120);