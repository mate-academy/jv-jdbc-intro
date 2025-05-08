CREATE DATABASE IF NOT EXISTS books;
USE books;
CREATE TABLE BOOKS (`id` bigint NOT NULL AUTO_INCREMENT, `title` varchar(255), `price` decimal, PRIMARY KEY (`id`));