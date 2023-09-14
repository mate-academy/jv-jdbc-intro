DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255),
    `price` DECIMAL(10, 3),
    PRIMARY KEY (`id`)
);