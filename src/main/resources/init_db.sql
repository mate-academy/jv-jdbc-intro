USE `mydatabase`;
    CREATE TABLE `books` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `title` VARCHAR(255),
        `price` BIGINT,
        PRIMARY KEY (`id`)
    );

