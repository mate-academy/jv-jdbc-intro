CREATE TABLE `library`.`books` (
`id` bigint NOT NULL AUTO_INCREMENT,
`title` VARCHAR(255) NULL DEFAULT NULL,
`price` DECIMAL(10) NULL DEFAULT NULL,
`is_deleted` tinyint NOT NULL DEFAULT '0',
PRIMARY KEY (`id`));
