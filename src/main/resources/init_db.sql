USE jdbc_intro;

DROP TABLE IF EXISTS books;

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

INSERT INTO books (id, title, price) VALUES
    (1, 'Learning Java', 29.99),
    (2, 'Effective Java', 45.50),
    (3, 'Java: The Complete Reference', 59.99);