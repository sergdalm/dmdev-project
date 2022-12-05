INSERT INTO users(id, email, mobile_phone_number, role, password, first_name, last_name)
VALUES (1, 'dmitry@gmail.com', '+7(911)749-94-28', 'SPECIALIST', '{noop}6403uh', 'Dmitry', 'Cheremuhin'),
       (2, 'natali@gmail.com', '+7(911)493-09-02', 'SPECIALIST', '{noop}093jrnd', 'Natali', 'Kremneva'),
       (3, 'alex@gmail.com', '+7(911)332-65-23', 'ADMINISTRATOR', '{noop}fj04ff', 'Alex', 'S'),
       (4, 'svetlana@gmail.com', '+7(911)849-93-13', 'CLIENT', '{noop}28ff', 'Svetlana', 'Cheremuhina'),
       (5, 'marina@gmail.com', '+7(911)492-06-02', 'CLIENT', '{noop}23d0i9', 'Marina', 'Ivanova'),
       (6, 'katya@gmail.com', '+7(911)214-05-91', 'CLIENT', '{noop}wc09jn', 'Katya', 'Petrova');

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO user_info(user_id,gender, birthday, registered_at, description)
VALUES (1, 'MALE', '1975-05-25', '2022-11-14 13:23:00.000000', 'Work experience: 3 years'),
       (2, 'FEMALE', '1983-04-29', '2022-11-14 15:24:00.000000', 'Work experience: 10 years'),
       (3,  'FEMALE', '1993-07-12', '2022-11-14 15:24:00.000000', null),
       (4, 'FEMALE', '1955-04-24', '2022-11-14 15:24:00.000000', null),
       (5, 'FEMALE', '1990-07-20', '2022-11-15 13:00:00.000000', null),
       (6, 'FEMALE', '1995-09-01', '2022-11-16 13:15:00.000000', null);

INSERT INTO service(id, name, description)
VALUES (1, 'CLASSIC_MASSAGE', 'Relaxing massage'),
       (2, 'HONEY_MASSAGE', 'Good for health'),
       (3, 'CUPPING_MASSAGE', '');

SELECT SETVAL('service_id_seq', (SELECT MAX(id) FROM service));

INSERT INTO specialist_service(id, specialist_id, service_id, length_min, price)
VALUES (1, 1, 1, 60, 1000),
       (2, 1, 2, 60, 1000),
       (3, 2, 1, 60, 2000),
       (4, 2, 3, 60, 2000);

SELECT SETVAL('specialist_service_id_seq', (SELECT MAX(id) FROM specialist_service));

INSERT INTO review(id, specialist_id, client_id, published_at, content)
VALUES (1, 1, 4, '2022-11-14 14:23:16.000000', 'Good specialist');

SELECT SETVAL('review_id_seq', (SELECT MAX(id) FROM review));

INSERT INTO address(id, address_name, description)
VALUES (1, 'Narvskaya', 'Prospect stacheck, 50'),
       (2, 'Admiralteyskaya', 'Malaya Moraskaya, 16');

SELECT SETVAL('address_id_seq', (SELECT MAX(id) FROM address));

INSERT INTO appointment(id, client_id, specialist_id, address_id, service_id, date, start_time, length_min, price, status)
VALUES (1, 4, 1, 1, 1, '2022-10-20', '12:00:00', 60, 1000, 'CANCELED'),
       (2, 4, 1, 1, 1, '2022-10-21', '13:00:00', 60, 1000, 'COMPLETED_PAID'),
       (3, 4, 1, 1, 1, '2022-10-29', '14:00:00', 60, 1000, 'COMPLETED_NOT_PAID'),
       (4, 5, 1, 2, 2, '2022-10-25', '16:00:00', 60, 1000, 'COMPLETED_PAID'),
       (5, 5, 1, 2, 2, '2022-11-25', '16:00:00', 60, 1000, 'CONFIRMED_AND_SCHEDULED'),
       (6, 5, 1, 1, 1, '2022-11-30', '18:00:00', 60, 1000, 'CHANGED_NOT_CONFIRMED'),
       (7, 5, 1, 2, 1, '2022-12-15', '18:00:00', 60, 1000, 'CREATED_NOT_CONFIRMED'),
       (8, 6, 2, 1, 2, '2022-11-20', '12:00:00', 60, 2000, 'COMPLETED_PAID'),
       (9, 6, 2, 1, 2, '2022-11-26', '12:00:00', 60, 2000, 'CONFIRMED_AND_SCHEDULED');

SELECT SETVAL('appointment_id_seq', (SELECT MAX(id) FROM appointment));

INSERT INTO specialist_available_time(id, specialist_id, address_id, date, time)
VALUES (1, 1, 1, '2022-11-26', '12:00:00'),
       (2, 1, 1, '2022-11-26', '13:00:00'),
       (3, 1, 1, '2022-11-26', '14:00:00'),
       (4, 1, 2, '2022-11-27', '16:00:00'),
       (5, 1, 2, '2022-11-27', '17:00:00'),
       (6, 1, 2, '2022-11-27', '18:00:00'),
       (7, 2, 2, '2022-11-27', '13:00:00'),
       (8, 2, 2, '2022-11-27', '14:00:00'),
       (9, 2, 2, '2022-11-27', '15:00:00'),
       (10, 2, 2, '2022-11-28', '18:00:00'),
       (11, 2, 2, '2022-11-28', '19:00:00'),
       (12, 2, 2, '2022-11-28', '20:00:00');

SELECT SETVAL('specialist_available_time_id_seq', (SELECT MAX(id) FROM specialist_available_time));

