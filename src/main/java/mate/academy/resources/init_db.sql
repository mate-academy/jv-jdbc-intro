CREATE TABLE `book` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `title` varchar(200) DEFAULT NULL,
                        `price` decimal(10,0) DEFAULT NULL,
                        `is_deleted` tinyint DEFAULT '0',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3