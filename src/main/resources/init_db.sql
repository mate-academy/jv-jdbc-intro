CREATE DATABASE Library;

CREATE TABLE `books`
(
    `id`    INT NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(255),
    `price` INT,
    PRIMARY KEY (`id`)
);

INSERT INTO books(title, price) VALUES ('Властелин колец', 500);
INSERT INTO books(title, price) VALUES ('Гордость и предубеждение', 300);
INSERT INTO books(title, price) VALUES ('Тёмные начала', 250);
INSERT INTO books(title, price) VALUES ('Автостопом по галактике', 700);
INSERT INTO books(title, price) VALUES ('Гарри Поттер и Кубок огня', 800);
INSERT INTO books(title, price) VALUES ('Убить пересмешника', 350);
INSERT INTO books(title, price) VALUES ('1984', 200);
INSERT INTO books(title, price) VALUES ('Сто лет одиночества', 150);
INSERT INTO books(title, price) VALUES ('Великий Гэтсби', 650);
INSERT INTO books(title, price) VALUES ('Граф Монте-Кристо', 600);
