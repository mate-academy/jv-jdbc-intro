DROP TABLE IF EXISTS Books;
CREATE TABLE books(
                     id BIGINT NOT NULL AUTO_INCREMENT,
                     title VARCHAR(255) NOT NULL,
                     author VARCHAR(255) NOT NULL,
                     isbn VARCHAR(13) UNIQUE,
                     published_date DATE,
                     price DECIMAL,
                     PRIMARY KEY (id)
                 );