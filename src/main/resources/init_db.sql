# DROP DATABASE IF EXISTS `intro_library_db`;
CREATE DATABASE `intro_library_db` DEFAULT CHARACTER SET utf8mb4;

# DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`
(
    `id`         bigint  NOT NULL AUTO_INCREMENT,
    `title`      varchar(255)     DEFAULT NULL,
    `price`      decimal(10, 0)   DEFAULT NULL,
    `is_deleted` tinyint NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

SELECT * FROM books;


