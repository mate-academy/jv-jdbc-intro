CREATE DATABASE `books_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `books` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `title` varchar(255) DEFAULT NULL,
                         `price` decimal(11,0) DEFAULT NULL,
                         `is_deleted` tinyint DEFAULT '0',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

