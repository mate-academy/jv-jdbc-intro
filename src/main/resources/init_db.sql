CREATE DATABASE db;

USE db;

CREATE TABLE IF NOT EXISTS books(
    `id` bigint NOT NULL  AUTO_INCREMENT,
    `title` varchar(255) NOT NULL,
    `price` bigint NOT NULL,
    PRIMARY KEY (id)
);
