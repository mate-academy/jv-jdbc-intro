use books_store_db;
CREATE TABLE IF NOT EXISTS books
(
    id BIGINT NOT NULL
AUTO_INCREMENT PRIMARY KEY,
    title TEXT NOT NULL,
    price BIGINT NOT NULL
);
