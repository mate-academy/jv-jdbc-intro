CREATE SCHEMA `library_db` DEFAULT CHARACTER SET utf8;

USE library_db;

CREATE TABLE books(
    id BIGINT UNIQUE AUTO_INCREMENT,
    title VARCHAR(255),
    price DECIMAL,

    PRIMARY KEY (id)
);
