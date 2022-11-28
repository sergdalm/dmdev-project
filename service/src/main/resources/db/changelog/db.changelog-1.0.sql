--liquibase formatted sql

--changeset asergienko:1
CREATE SCHEMA IF NOT EXISTS massage;

SET search_path TO massage;

--changeset asergienko:2
CREATE TABLE users
(
    id                  SERIAL PRIMARY KEY,
    email               VARCHAR(128) UNIQUE NOT NULL,
    mobile_phone_number VARCHAR(64) UNIQUE  NOT NULL,
    role                VARCHAR(32)         NOT NULL,
    password            VARCHAR(128)        NOT NULL
);
--rollback DROP TABLE massage.users;

--changeset asergienko:3
CREATE TABLE massage.user_info
(
    user_id       INT PRIMARY KEY REFERENCES users (id),
    first_name    VARCHAR(128) NOT NULL,
    last_name     VARCHAR(128) NOT NULL,
    gender        VARCHAR(32)  NOT NULL,
    birthday      DATE         NOT NULL,
    registered_at TIMESTAMP    NOT NULL,
    description   VARCHAR(256)
);
--rollback DROP TABLE massage.user_info;

--changeset asergienko:4
CREATE TABLE massage.service
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(128) UNIQUE NOT NULL,
    description VARCHAR(256)        NOT NULL
);
--rollback DROP TABLE massage.service;

--changeset asergienko:5
CREATE TABLE massage.specialist_service
(
    id            SERIAL PRIMARY KEY,
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    service_id    INT REFERENCES service (id) ON DELETE CASCADE NOT NULL,
    length_min    INT                                           NOT NULL,
    price         INT                                           NOT NULL,
    UNIQUE (specialist_id, service_id, length_min)
);
--rollback DROP TABLE massage.specialist_service;

--changeset asergienko:6
CREATE TABLE massage.address
(
    id           SERIAL PRIMARY KEY,
    address_name VARCHAR(128) UNIQUE NOT NULL,
    description  VARCHAR(256)        NOT NULL
);
--rollback DROP TABLE massage.address;

--changeset asergienko:7
CREATE TABLE massage.specialist_available_time
(
    id            SERIAL PRIMARY KEY,
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    address_id    INT REFERENCES address (id) ON DELETE CASCADE NOT NULL,
    date          DATE                                          NOT NULL,
    time          TIME                                          NOT NULL,
    UNIQUE (specialist_id, address_id, date, time)
);
--rollback DROP TABLE massage.specialist_available_time;

--changeset asergienko:8
CREATE TABLE massage.appointment
(
    id            SERIAL PRIMARY KEY,
    client_id     INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    address_id    INT REFERENCES address (id) ON DELETE CASCADE NOT NULL,
    service_id    INT REFERENCES service (id) ON DELETE CASCADE NOT NULL,
    date          DATE                                          NOT NULL,
    start_time    TIME                                          NOT NULL,
    length_min    INT                                           NOT NULL,
    price         INT                                           NOT NULL,
    status        VARCHAR(32)                                   NOT NULL,
    UNIQUE (specialist_id, date, start_time)
);
--rollback DROP TABLE massage.appointment;

--changeset asergienko:9
CREATE TABLE massage.review
(
    id            SERIAL PRIMARY KEY,
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    client_id     INT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    published_at  TIMESTAMP                                   NOT NULL,
    content       VARCHAR(256)                                NOT NULL
);
--rollback DROP TABLE massage.review;

--changeset asergienko:10
CREATE TABLE massage.service_sale
(
    id                    SERIAL PRIMARY KEY,
    specialist_service_id INT REFERENCES specialist_service (id) ON DELETE CASCADE NOT NULL,
    address_id            INT REFERENCES address (id) ON DELETE CASCADE            NOT NULL,
    start_date            DATE                                                     NOT NULL,
    duration_days         INT                                                      NOT NULL,
    sale_price            INT                                                      NOT NULL
);
--rollback DROP TABLE massage.service_sale;