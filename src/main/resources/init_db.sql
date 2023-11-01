USE jdbc;

CREATE TABLE Book (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255) NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO book(title, price)
VALUES('Climate Resilience', 1200);

INSERT INTO book(title, price)
VALUES('Neurofitness', 720);

INSERT INTO book(title, price)
VALUES('Dopamine Nation', 360);
