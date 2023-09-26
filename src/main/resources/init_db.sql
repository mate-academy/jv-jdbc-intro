use books_store_db;
CREATE TABLE IF NOT EXISTS books
(
    id    bigint auto_increment
        primary key,
    title text   null,
    price bigint null
);
