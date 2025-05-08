CREATE TABLE `books` (
`id` BIGINT AUTO_INCREMENT,
`title` VARCHAR(255),
`price` DECIMAL(5,2),
PRIMARY KEY (`id`)
);

INSERT INTO books (title, price) VALUES(
'Whats the Point of Maths?', 200
);
INSERT INTO books (title, price) VALUES(
'Upstream Intermediate Workbook', 350
);
INSERT INTO books (title, price) VALUES(
'See Inside Your Body', 400
);
INSERT INTO books (title, price) VALUES(
'Henry Ford: My life and My work', 250
);
INSERT INTO books (title, price) VALUES(
'GCSE Edexcel Mathematics for the Grade 9-1 Course', 350
);
