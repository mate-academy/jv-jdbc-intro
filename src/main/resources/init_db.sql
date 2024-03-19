CREATE DATABASE `books_schema`;
CREATE TABLE `books` (
  `id` bigint PRIMARY KEY AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `price` decimal(10,2) unsigned DEFAULT NULL);