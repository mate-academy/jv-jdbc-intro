CREATE SCHEMA jdbc_intro;

USE jdbc_intro;

CREATE TABLE Book
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    price INT
);