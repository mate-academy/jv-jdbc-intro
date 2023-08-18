CREATE TABLE BOOK (
                      book_id BIGINT NOT NULL AUTO_INCREMENT,
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      publication_year INT,
                      genre VARCHAR(100),
                      price DECIMAL(10, 2)
);
