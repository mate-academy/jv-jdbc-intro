CREATE DATABASE mate;
CREATE TABLE books (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      title VARCHAR(255) DEFAULT NULL,
                      price DECIMAL DEFAULT 0,
                      is_deleted TINYINT NOT NULL DEFAULT 0,
                      PRIMARY KEY (id)
) ENGINE = InnoDB CHARSET = utf8;
