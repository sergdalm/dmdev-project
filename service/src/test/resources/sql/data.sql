INSERT INTO users(id, email, mobile_phone_number, role, password)
VALUES (1, 'igor@gmail.com', '+7(911)749-94-28', 'SPECIALIST', '6403uh'),
       (2, 'natali@gmail.com', '+7(911)493-09-02', 'SPECIALIST', '093jrnd');

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO user_info(user_id, first_name, last_name, gender, birthday, registered_at, description)
VALUES (1, 'Igor', 'Zerevushkin', 'MALE', '1975-05-26', '2022-11-14 13:23:16.000000', 'Work experience: 3 years'),
       (2, 'Marina', 'Kremneva', 'FEMALE', '1973-04-29', '2022-11-14 15:24:16.000000', 'Work experience: 10 years');