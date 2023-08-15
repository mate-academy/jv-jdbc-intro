CREATE DATABASE books;
       USE books;
CREATE TABLE books
(
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255),
    `price` DECIMAL(10,2),
    PRIMARY KEY (`id`)
);
