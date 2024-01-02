CREATE DATABASE `library`;
USE library;
CREATE TABLE `books` (
    -> `id` BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    -> `title` VARCHAR(255),
    -> `price` BIGINT
    -> );