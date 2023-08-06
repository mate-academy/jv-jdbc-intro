CREATE SCHEMA IF NOT EXISTS `library_db` DEFAULT CHARACTER SET utf8;
USE `library_db`;
CREATE TABLE `books`
(
    `id`         bigint  NOT NULL AUTO_INCREMENT,
    `title`      varchar(255)     DEFAULT NULL,
    `price`      decimal(10, 0)   DEFAULT NULL,
    `is_deleted` tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
);