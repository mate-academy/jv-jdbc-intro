CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    number_plate VARCHAR(20) UNIQUE NOT NULL,
    mileage INT NOT NULL CHECK (mileage >= 0),
    was_in_accident BOOLEAN NOT NULL DEFAULT FALSE
);
