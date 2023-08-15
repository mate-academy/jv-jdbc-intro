CREATE SCHEMA IF NOT EXISTS `book_db` DEFAULT CHARACTER SET utf8;
USE
`book_db`;
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books`
(
    `id`         BIGINT(11) NOT NULL AUTO_INCREMENT,
    `title`      VARCHAR(225) NOT NULL,
    `price`      BIGINT(11) NOT NULL,
    `is_deleted` TINYINT      NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);
