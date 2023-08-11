CREATE DATABASE library

create table library.books
(
    id    bigint auto_increment primary key,
    title text,
    price decimal
);
