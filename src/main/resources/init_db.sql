CREATE TABLE IF NOT EXISTS `books` (`id` bigint NOT NULL AUTO_INCREMENT,
                                    `title` varchar(24) DEFAULT NULL,
                                    `price` decimal(9,2),
                                     PRIMARY KEY(id));

