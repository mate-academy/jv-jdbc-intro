CREATE DATABASE `library_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  `literary_format_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `books_literary_formats_fk` (`literary_format_id`),
  CONSTRAINT `books_literary_formats_fk` FOREIGN KEY (`literary_format_id`) REFERENCES `literary_formats` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;