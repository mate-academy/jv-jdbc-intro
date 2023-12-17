CREATE DATABASE IF NOT EXISTS `book_db` DEFAULT CHARACTER SET utf8mb4;
USE `book_db`;
CREATE TABLE `books`(
    `id`         bigint  NOT NULL AUTO_INCREMENT,
    `title`      varchar(255)     DEFAULT NULL,
    `price`      decimal(10, 2)   DEFAULT NULL,
    `is_deleted` tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
