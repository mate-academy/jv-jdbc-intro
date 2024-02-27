CREATE SCHEMA `books_db` DEFAULT CHARACTER SET utf8;
USE `books_db`;
CREATE TABLE `books` (
                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `title` varchar(255) NOT NULL,
                         `price` decimal NOT NULL,
                         `is_deleted` tinyint(4) NOT NULL DEFAULT '0',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
