CREATE TABLE IF NOT EXISTS test.books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
    );


DELETE from books where id in (2,3,4,5,6);

truncate table books;

select * from books;
