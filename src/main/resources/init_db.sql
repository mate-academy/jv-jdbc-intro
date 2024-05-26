CREATE DATABASE jdbcIntro;
USE jdbcIntro;
CREATE TABLE Book (
                      id bigint NOT NULL AUTO_INCREMENT primary key,
                      title varchar(255),
                      price decimal(10,2)
);