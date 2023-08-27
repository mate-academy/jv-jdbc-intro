CREATE TABLE `books` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `title` varchar(255) NOT NULL,
                          `price` decimal(10,2) DEFAULT NULL,
                          PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;