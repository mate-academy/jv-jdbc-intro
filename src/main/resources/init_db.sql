CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price FLOAT,
    PRIMARY KEY (id)
);

INSERT INTO books(title, price) VALUES ('To Kill a Mockingbird', 12.99);
INSERT INTO books(title, price) VALUES ('The Great Gatsby', 10.75);
INSERT INTO books(title, price) VALUES ('The Catcher in the Rye', 11.25);
INSERT INTO books(title, price) VALUES ('Pride and Prejudice', 17.99);