CREATE DATABASE `book_store_db`;

CREATE TABLE `book_store_db`.`books`
(
    `id`         INT(10)   NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(255) NOT NULL,
    `price`      DECIMAL      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;
