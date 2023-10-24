DROP SCHEMA IF EXISTS `mate_academy_hibernate`;

CREATE SCHEMA IF NOT EXISTS `mate_academy_hibernate` DEFAULT CHARACTER SET utf8mb3;

USE `mate_academy_hibernate`;

CREATE TABLE IF NOT EXISTS `mate_academy_hibernate`.`books` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                            `title` VARCHAR(50) NULL DEFAULT NULL,
                            `price` DECIMAL(20,6) NULL DEFAULT NULL,
                            PRIMARY KEY (`id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 0
    DEFAULT CHARACTER SET = utf8mb3;
