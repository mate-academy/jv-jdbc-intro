CREATE TABLE `books` (
    id biginit NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price FLOAT,
    PRIMARY KEY(id)
);

INSERT INTO books(title, price) VALUES('A Brief History of Time', 24.13);
INSERT INTO books(title, price) VALUES('Atomic Habits', 16.35);
INSERT INTO books(title, price) VALUES('The Devil Wears Prada', 12.99);
INSERT INTO books(title, price) VALUES('The Shining', 9.99);
