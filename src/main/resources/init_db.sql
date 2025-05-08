CREATE TABLE IF NOT EXISTS `books` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `price` decimal(10, 2),
    PRIMARY KEY (`id`)
);
