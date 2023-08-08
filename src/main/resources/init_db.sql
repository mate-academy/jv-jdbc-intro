CREATE DATABASE `books`

CREATE TABLE `books`(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `title`      varchar(45) DEFAULT NULL,
    `price`      bigint      DEFAULT NULL,
    `is_deleted` tinyint(1) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
