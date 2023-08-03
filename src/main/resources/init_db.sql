create table sql7633485.books
(
    id    bigint auto_increment,
    title VARCHAR(255) null,
    price DECIMAL      null,
    is_deleted BIT DEFAULT false NOT NULL,
    constraint book_pk
        primary key (id)
);