CREATE SCHEMA `book` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `book`.`books` (
                                `id` BIGINT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
                                `title` VARCHAR(45) NOT NULL,
                                `price` DECIMAL NOT NULL,
                                `is_deleted` BIT(1) NOT NULL DEFAULT 0,
                                PRIMARY KEY (`id`));