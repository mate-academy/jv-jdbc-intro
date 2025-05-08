DROP TABLE IF EXISTS book;

CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    price DECIMAL(10, 2)
);

INSERT INTO book (title, price) VALUES ('Master', 19.99);
INSERT INTO book (title, price) VALUES ('Margarita', 6.66);