CREATE SCHEMA IF NOT EXISTS `book_db` DEFAULT CHARACTER SET utf8;
USE `book_db`;

CREATE TABLE `books` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(200) DEFAULT NULL,
                         `price` decimal(10,0) DEFAULT NULL,
                         `is_deleted` tinyint DEFAULT '0',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
