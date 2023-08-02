CREATE TABLE books (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       price DECIMAL(10, 2) NOT NULL,
                       is_deleted TINYINT NOT NULL DEFAULT 0
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3;