CREATE TABLE IF NOT EXISTS books (
                                     id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     title VARCHAR(255) NOT NULL,
                                     author VARCHAR(255),
                                     genre VARCHAR(100)
);