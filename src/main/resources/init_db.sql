CREATE SCHEMA `test_dao_db`;

CREATE TABLE test_dao_db.books (
                                   id INT auto_increment NOT NULL,
                                   title varchar(256) NOT NULL,
                                   price DECIMAL(10,2) NOT NULL,
                                   CONSTRAINT books_pk PRIMARY KEY (id)
)
    ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_0900_ai_ci;
