CREATE SCHEMA `book_shop`

CREATE TABLE `books` (
    `id` BIGINT NOT NULl AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `price` DECIMAL,
    PRIMARY KEY (`id`));
