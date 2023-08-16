CREATE TABLE `books`
(
    `id`    bigint         NOT NULL AUTO_INCREMENT,
    `title` varchar(45)    NOT NULL,
    `price` decimal(10, 2) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8mb3;