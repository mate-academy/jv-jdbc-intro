CREATE TABLE `book` (
                        `id` bigint NOT NULL,
                        `title` varchar(45) DEFAULT NULL,
                        `price` decimal(20,0) DEFAULT NULL,
                        `isDeleted` tinyint DEFAULT '0',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ciB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_cinnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci