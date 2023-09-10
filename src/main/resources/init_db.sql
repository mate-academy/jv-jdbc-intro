CREATE TABLE `books` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(256) DEFAULT NULL,
                         `price` decimal(10,0) DEFAULT NULL,
                         `is_deleted` tinyint NOT NULL DEFAULT '0',
                         UNIQUE KEY `books_pk` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

