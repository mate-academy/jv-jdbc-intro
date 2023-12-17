USE books;

CREATE TABLE Book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    price DECIMAL(19, 2)
);

INSERT INTO Book (title, price) VALUES
    ('The Great Gatsby', 29.99),
    ('To Kill a Mockingbird', 19.95),
    ('1984', 24.50),
    ('Pride and Prejudice', 14.99),
    ('The Catcher in the Rye', 17.75);

SELECT * FROM book;