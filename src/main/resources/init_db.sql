CREATE TABLE `books` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `title` varchar(45) DEFAULT NULL,
                         `price` bigint DEFAULT NULL,
                         `is_deleted` tinyint NOT NULL DEFAULT '0',
                         PRIMARY KEY (`id`)
)
