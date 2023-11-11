USE jdbc_hw_1;
CREATE TABLE books (
 id BIGINT NOT NULL AUTO_INCREMENT,
 title VARCHAR(255),
 price decimal(10,2),
 PRIMARY KEY (id)
);