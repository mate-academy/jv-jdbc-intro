CREATE DATABASE `book`;
USE book;
CREATE TABLE `books` (
`id` BIGINT AUTO_INCREMENT,
`title` VARCHAR(255),
`price` BigDecimal,
PRIMARY KEY(`id`)
);