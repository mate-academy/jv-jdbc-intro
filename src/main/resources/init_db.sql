CREATE DATABASE `book_shop` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `books` (
                         `id` bigint AUTO_INCREMENT,
                         `title` varchar(255) NOT NULL,
                         `price` decimal(10,0) NOT NULL,
                         `is_deleted` tinyint NOT NULL DEFAULT '0',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3;
