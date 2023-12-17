DROP TABLE IF EXISTS test.books;
CREATE TABLE test.books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255),
    price NUMERIC,
    PRIMARY KEY (id)
);