DROP TABLE IF EXISTS books;

CREATE TABLE books (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  price DECIMAL(10,2) NOT NULL
);

DELETE FROM books;

INSERT INTO books (title, price) VALUES
  ('The Pragmatic Programmer', 42.50),
  ('Clean Code', 37.99),
  ('Introduction to Algorithms', 59.95),
  ('Design Patterns', 45.00),
  ('Refactoring', 39.90);
