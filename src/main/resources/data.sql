TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE product RESTART IDENTITY CASCADE;


INSERT INTO users (username) VALUES ('Начальный Пользователь 1');
INSERT INTO users (username) VALUES ('Начальный Пользователь 2');

INSERT INTO product (account, balance, type, user_id)
VALUES (111122, 50000.00, 'Дебетовая карта', 1);

INSERT INTO product (account, balance, type, user_id)
VALUES (333344, -1500.50, 'Кредитный счет', 1);

INSERT INTO product (account, balance, type, user_id)
VALUES (555566, 1200000.75, 'Сберегательный вклад', 2);