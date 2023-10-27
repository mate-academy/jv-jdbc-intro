USE sql_db;
CREATE TABLE books (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(40),
    price INT,
    PRIMARY KEY (id)
);
