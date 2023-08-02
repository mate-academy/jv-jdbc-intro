CREATE SCHEMA IF NOT EXISTS `books_app` DEFAULT CHARACTER SET utf8;
USE `books_app`;

DROP TABLE IF EXISTS `books`;

CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `price` decimal(6,2) NOT NULL DEFAULT '0',
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
