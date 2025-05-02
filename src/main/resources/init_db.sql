CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price FLOAT,
    PRIMARY KEY(id)
);

INSERT INTO books(title, price) VALUES ('The Great Gatsby', 17.00);

INSERT INTO books(title, price) VALUES ('Hitler: A Study in Tyranny by Alan Bullock', 10.75);

INSERT INTO books(title, price) VALUES ('1984', 9.99);

INSERT INTO books(title, price) VALUES ('To Kill a Mockingbird', 17.99);
