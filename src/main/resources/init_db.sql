CREATE DATABASE IF NOT EXISTS `book_shop`;
    CREATE TABLE `books` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `title` varchar(225),
        `price` decimal,
        PRIMARY KEY (`id`)
    );