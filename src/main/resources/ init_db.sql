CREATE TABLE books (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO books (title, price) VALUES ('Kobzar', 29.99);
INSERT INTO books (title, price) VALUES ('Stone cutters', 16.50);
INSERT INTO books (title, price) VALUES ('Shadows of Forgotten Ancestors', 22.25);
