CREATE DATABASE IF NOT EXISTS `book_shop`;
    CREATE TABLE `books` (
        `id` bigint NOT NULL AUTO_INCREMENT,
        `title` varchar(225) NOT NULL,
        `price` decimal(10, 2),
        PRIMARY KEY (`id`)
    );