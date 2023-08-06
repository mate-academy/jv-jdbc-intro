CREATE DATABASE `book_service`;

CREATE TABLE `books` (
                         `id` int not null auto_increment,
                         `title` varchar(255) null,
                         `price` decimal  null,
                         `is_deleted` tinyint not null,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

