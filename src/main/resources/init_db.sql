CREATE SCHEMA `book_db` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `price` DECIMAL DEFAULT '0',
  `is_deleted` tinyint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3