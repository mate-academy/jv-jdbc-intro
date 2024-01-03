CREATE TABLE `books` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(225) NOT NULL,
                         `price` decimal(65,0) NOT NULL,
                         `is_deleted` boolean NOT NULL DEFAULT false,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `id_UNIQUE` (`id`)
);