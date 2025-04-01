CREATE TABLE IF NOT EXISTS books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    price DECIMAL(10,2) NOT NULL
);
INSERT INTO books (title, price) VALUES
                                     ('Java for Beginners', 49.99),
                                     ('Advanced Java', 79.99),
                                     ('MySQL', 99.99),
                                     ('Java Hibernate', 38.80);
