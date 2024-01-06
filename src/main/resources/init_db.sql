CREATE DATABASE `jv-jdbc-intro`;

USE `jv-jdbc-intro`;

CREATE TABLE `books` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(255),
  `price` DECIMAL(15, 2)
);