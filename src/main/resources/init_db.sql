USE test;
CREATE TABLE books (
  id bigint NOT NULL AUTO_INCREMENT ,
  title varchar(50) NOT NULL ,
  price decimal NOT NULL ,
  primary key (id)
)