USE sql_db;
CREATE TABLE books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(40),
    price INT,
    PRIMARY KEY (id)
);
