use books_store_db;
create table books
(
    id    bigint auto_increment
        primary key,
    title text   null,
    price bigint null
);
