USE `mydatabase`;
    CREATE TABLE `books` (
        `id` BIGINT NOT NULL AUTO_INCREMENT,
        `title` VARCHAR(255),
        `price` BIGINT,
        PRIMARY KEY (`id`)
    );
INSERT INTO books(title, price)
VALUES ('The Great Gatsby', 300);
INSERT INTO books(title, price)
VALUES ('Animal Farm', 150);
INSERT INTO books(title, price)
VALUES ('The Little Prince', 200);
INSERT INTO books(title, price)
VALUES ('The Catcher in the Rye', 400);
INSERT INTO books(title, price)
VALUES ('Lord of the Flies', 120);

