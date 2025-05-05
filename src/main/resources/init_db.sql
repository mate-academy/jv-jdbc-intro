CREATE TABLE books (
                       id INT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each book
                       title VARCHAR(255) NOT NULL,       -- Title of the book
                       price DECIMAL(10, 2) NOT NULL      -- Price of the book with two decimal points
);