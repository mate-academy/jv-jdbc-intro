CREATE DATABASE IF NOT EXISTS `intro`;

USE `intro`;

CREATE TABLE `books` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255),
    `price` INT,
    PRIMARY KEY (`id`)
);

INSERT INTO `books` (`title`, `price`) VALUES
('The Catcher in the Rye', 120),
('To Kill a Mockingbird', 150),
('1984', 200),
('The Great Gatsby', 180),
('Moby Dick', 220);
