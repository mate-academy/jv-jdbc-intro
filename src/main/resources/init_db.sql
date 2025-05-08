CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    title VARCHAR(255) NOT NULL ,
    price DECIMAL NOT NULL
);

INSERT INTO books(title, price) VALUES('A Brief History of Time', 24.13);
INSERT INTO books(title, price) VALUES('Atomic Habits', 16.35);
INSERT INTO books(title, price) VALUES('The Devil Wears Prada', 12.99);
INSERT INTO books(title, price) VALUES('Java: The Complete Reference', 20.99);
