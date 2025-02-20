CREATE TABLE `books`.`books` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `price` DECIMAL NOT NULL,
    PRIMARY KEY (`id`));

INSERT INTO books (title, price) VALUES('First Book', 230),
                                       ('Second Book', 120),
                                       ('Third Book', 222);