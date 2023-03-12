
ALTER TABLE users
ADD COLUMN second_name VARCHAR(64);

INSERT INTO users(id, last_name, first_name, second_name)
VALUES (1, 'Kowalsky', 'John', null),
       (2, 'Smith', 'Johanna', null),
       (3, 'Nowak', 'Adam', 'Piotr'),
       (4, 'April', 'Agata', 'Marta');