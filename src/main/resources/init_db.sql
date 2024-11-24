-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema jdbcIntro
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema jdbcIntro
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `jdbcIntro` DEFAULT CHARACTER SET utf8;
USE `jdbcIntro`;

-- -----------------------------------------------------
-- Table `jdbcIntro`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `jdbcIntro`.`book`
(
    `id`    INT         NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(45) NULL,
    `price` DECIMAL     NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

TRUNCATE TABLE `jdbcIntro`.`book`;

INSERT INTO `book` (title, price)
VALUES ('title_1', 1),
       ('title_2', 2),
       ('title_3', 3),
       ('title_4', 4),
       ('title_5', 5),
       (NULL, 6),
       ('title_7', NULL);

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
