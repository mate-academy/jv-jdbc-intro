CREATE TABLE IF NOT EXISTS books (
                                     id    BIGSERIAL PRIMARY KEY,
                                     title VARCHAR(255) NOT NULL,
                                     price DECIMAL(10, 2) NOT NULL
);
