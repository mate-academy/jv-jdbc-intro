CREATE TABLE `books` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `title` varchar(45) DEFAULT 'null',
                         `price` decimal(65,0) DEFAULT NULL,
                         `is_deleted` tinyint DEFAULT '0',
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3