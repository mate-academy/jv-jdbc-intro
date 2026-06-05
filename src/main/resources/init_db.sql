CREATE SCHEMA IF NOT EXISTS `mate_academy` DEFAULT CHARACTER SET utf8;
USE `mate_academy`;

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `title` VARCHAR(255) NOT NULL,
                         `price` DECIMAL(10, 2) NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8mb4;