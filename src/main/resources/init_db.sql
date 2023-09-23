CREATE DATABASE 'homework';

USE `homework`;

CREATE TABLE `books` (
`id` BIGINT NOT NULL AUTO_INCREMENT,
`title` VARCHAR(255),
`price` NUMERIC,
PRIMARY KEY (`id`)
);
