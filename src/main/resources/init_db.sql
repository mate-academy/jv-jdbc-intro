CREATE SCHEMA IF NOT EXISTS `library` DEFAULT CHARACTER SET utf8;
USE `library`;

CREATE TABLE `books` (
                                 `id` BIGINT(11) NOT NULL AUTO_INCREMENT,
                                 `title` VARCHAR(225) NOT NULL,
                                 `price` DECIMAL(10,2) NOT NULL,
                                 `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                 PRIMARY KEY (`id`));