CREATE
DATABASE `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `books`
(
    `id`         int NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) DEFAULT NULL,
    `title`      varchar(255) DEFAULT NULL,
    `is_deleted` tinyint      DEFAULT '0',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
