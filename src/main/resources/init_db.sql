USE book;

CREATE TABLE IF NOT EXISTS books(
	id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title varchar(255) NULL,
    price int NULL
);