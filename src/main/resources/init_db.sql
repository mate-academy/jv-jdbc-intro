CREATE DATABASE IF NOT EXISTS book DEFAULT CHARACTER SET utf8;

USE book;

CREATE TABLE IF NOT EXISTS Books (
                       id BIGINT AUTO_INCREMENT,
                       title VARCHAR(255) NOT NULL,
                       price int(10) NOT NULL,
                       PRIMARY KEY (id)
);

INSERT INTO Books (title, price) VALUES ('A Christmas Carol', 9);
INSERT INTO Books (title, price) VALUES ('1984', 11);
INSERT INTO Books (title, price) VALUES ('Don Quixote', 15);
INSERT INTO Books (title, price) VALUES ('Animal farm', 7);
INSERT INTO Books (title, price) VALUES ('War and Peace', 20);