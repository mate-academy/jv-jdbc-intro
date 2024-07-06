CREATE TABLE `books` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `title` varchar(45) DEFAULT NULL,
                         `price` decimal(10,2) DEFAULT NULL,
                         `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

