CREATE SCHEMA IF NOT EXISTS `library_db` DEFAULT CHARACTER SET utf8;
USE `library_db`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `title` varchar(255) DEFAULT NULL,
                         `price` decimal(10,0) DEFAULT NULL,
                         `is_deleted` tinyint NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;

SET FOREIGN_KEY_CHECKS = 1;