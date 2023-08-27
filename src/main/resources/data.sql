INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('VIP Restaurant'),
       ('Cheap Restaurant'),
       ('Italian Restaurant'),
       ('Chinese Restaurant');

INSERT INTO FOOD (name, restaurant_id, date, price)
VALUES ('TEA', 1, '2023-07-31', 100),
       ('TEA', 2, now(), 50),
       ('FRIED EGGS', 1, '2023-07-31', 250),
       ('FRIED EGGS', 1, now(), 999),
       ('TOASTS', 2, now(), 150),
       ('BURGER', 3, '2023-01-01', 350);

INSERT INTO VOTE (user_id, date_time, restaurant_id)
VALUES (1, now(), 2),
       (1, '2023-08-01 10:00', 1),
       (2, now(), 3),
       (3, now(), 1),
       (3, '2023-08-02 09:00', 2),
       (3, '2023-08-01 09:00', 2);