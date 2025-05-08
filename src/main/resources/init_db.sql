USE test;
CREATE TABLE books (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    price INT
);
INSERT INTO books (title, price) VALUES ('Lord of Rings', 55);
INSERT INTO books (title, price) VALUES ('Hobbit', 60);
INSERT INTO books (title, price) VALUES ('Hungry Games', 54);
INSERT INTO books (title, price) VALUES ('Fantastic Beasts', 72);