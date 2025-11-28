DROP TABLE books;
CREATE TABLE books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price DECIMAL(8,2),
    PRIMARY KEY (id)
);