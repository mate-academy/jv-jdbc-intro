CREATE DATABASE `library_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `books` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(255) DEFAULT NULL,
                         `price` int DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
