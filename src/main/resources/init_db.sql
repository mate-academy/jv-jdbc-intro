CREATE DATABASE `library`
CREATE TABLE `library`.`books` (
                                   `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                                   `title` VARCHAR(45) NOT NULL,
                                   `price` DECIMAL NOT NULL,
                                   `is_deleted` TINYINT(1) NULL DEFAULT 0,
                                   PRIMARY KEY (`id`)
);