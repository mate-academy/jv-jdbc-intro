create schema if not exists test;

create table if not exists book (
    book_id serial primary key,
    title varchar(20),
    price decimal
);