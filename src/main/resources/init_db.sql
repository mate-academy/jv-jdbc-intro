CREATE SCHEMA IF NOT EXISTS `book_db` DEFAULT CHARACTER SET utf8;
USE `book_db`;

CREATE TABLE `books` (
            `id` bigint NOT NULL AUTO_INCREMENT,
            `title` varchar(255) NOT NULL,
            `price` decimal(10,2) DEFAULT NULL,
             `is_deleted` tinyint NOT NULL DEFAULT '0',
            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
