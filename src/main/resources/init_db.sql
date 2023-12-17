CREATE DATABASE `hw`;
USE hw;
CREATE TABLE `books` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `title` VARCHAR(100) NOT NULL,
                         `price` DECIMAL(15,9) NOT NULL,
                         PRIMARY KEY(`id`)
);