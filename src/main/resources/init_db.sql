CREATE DATABASE `book_store_db`;

CREATE TABLE `book_store_db`.`books`
(
    `id`         BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(255) NOT NULL,
    `price`      DECIMAL      NOT NULL,
    `is_deleted` tinyint      NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;

