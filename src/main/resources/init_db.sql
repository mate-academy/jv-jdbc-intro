CREATE SCHEMA IF NOT EXISTS `jdbc_intro`;
USE `jdbc_intro`;

DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `price` decimal(10,5) NOT NULL,
  `is_deleted` TINYINT DEFAULT false,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


