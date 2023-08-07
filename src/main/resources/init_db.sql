CREATE SCHEMA IF NOT EXISTS `library_db` DEFAULT CHARACTER SET utf8;

CREATE TABLE `library_db`.`books` (
                `id` BIGINT NOT NULL AUTO_INCREMENT,
                `title` VARCHAR(255) NULL DEFAULT NULL,
                `price` DECIMAL NULL DEFAULT NULL,
                `is_deleted` TINYINT NULL DEFAULT 0,
                PRIMARY KEY (`id`));