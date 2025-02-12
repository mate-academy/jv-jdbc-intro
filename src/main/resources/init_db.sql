CREATE TABLE `books` (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                         `title` varchar(255) NOT NULL,
                         `price` decimal(10,2) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci