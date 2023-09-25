USE test;

CREATE TABLE IF NOT EXISTS `books` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `price` NUMERIC(10, 2) NOT NULL,
    PRIMARY KEY (`id`)
    );