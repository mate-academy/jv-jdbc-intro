USE library;

CREATE TABLE books (
                    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    title VARCHAR(255) NOT NULL,
                    price DECIMAL(10, 2) NOT NULL
                   );