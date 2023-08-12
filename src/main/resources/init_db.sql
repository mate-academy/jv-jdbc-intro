DROP TABLE IF EXISTS books;

CREATE TABLE IF NOT EXISTS books
(   id    bigint auto_increment primary key,
    title varchar(255) not null,
    price double null
);
INSERT INTO books (id, title, price)
VALUES (1, 'Detective story', 532.74);
INSERT INTO books (id, title, price)
VALUES (2, 'Just Do It!', 142.15);
INSERT INTO books (id, title, price)
VALUES (3, 'Early morning in the hill', 12.10);
