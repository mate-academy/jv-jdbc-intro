CREATE TABLE `book`.`books` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT,
                                `title` VARCHAR(255) NULL,
                                `price` BIGINT NULL,
                                `is_deleted` TINYINT NOT NULL DEFAULT 0,
                                PRIMARY KEY (`id`));