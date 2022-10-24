CREATE DATABASE massage_project;

CREATE SCHEMA massage;

SET search_path TO massage;

CREATE TABLE users
(
    id                  SERIAL PRIMARY KEY,
    email               VARCHAR(128) UNIQUE NOT NULL,
    mobile_phone_number VARCHAR(64) UNIQUE  NOT NULL,
    password            VARCHAR(128)        NOT NULL
);


CREATE TABLE user_info
(
    user_id       INT PRIMARY KEY REFERENCES users (id),
    role          VARCHAR(32)  NOT NULL,
    first_name    VARCHAR(128) NOT NULL,
    last_name     VARCHAR(128) NOT NULL,
    gender        VARCHAR(32)  NOT NULL,
    birthday      DATE         NOT NULL,
    registered_at TIMESTAMP    NOT NULL,
    description   VARCHAR(256)
);

CREATE TABLE service
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(128) UNIQUE NOT NULL,
    description VARCHAR(256)        NOT NULL
);

CREATE TABLE specialist_service
(
    id            SERIAL PRIMARY KEY,
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    service_id    INT REFERENCES service (id) ON DELETE CASCADE NOT NULL,
    length_min    INT                                           NOT NULL,
    price         INT                                           NOT NULL,
    UNIQUE (specialist_id, service_id, length_min)
);


CREATE TABLE address
(
    id          SERIAL PRIMARY KEY,
    address     VARCHAR(128) UNIQUE NOT NULL,
    description VARCHAR(256)        NOT NULL
);


CREATE TABLE specialist_available_time
(
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    address_id    INT REFERENCES address (id) ON DELETE CASCADE NOT NULL,
    date          DATE                                          NOT NULL,
    time          TIME                                          NOT NULL,
    UNIQUE (specialist_id, address_id, date, time)
);

CREATE TABLE appointment
(
    id            SERIAL PRIMARY KEY,
    client_id     INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    address_id    INT REFERENCES address (id) ON DELETE CASCADE NOT NULL,
    service_id    INT REFERENCES service (id) ON DELETE CASCADE NOT NULL,
    date          DATE                                          NOT NULL,
    start_time    TIME                                          NOT NULL,
    length_min    INT                                           NOT NULL,
    prise         INT                                           NOT NULL,
    status        VARCHAR(32)                                   NOT NULL,
    price         INT                                           NOT NULL,
    UNIQUE (specialist_id, date, start_time)
);

CREATE TABLE review
(
    id            SERIAL PRIMARY KEY,
    specialist_id INT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    client_id     INT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    published_at  TIMESTAMP                                   NOT NULL,
    content       VARCHAR(256)                                NOT NULL
);

CREATE TABLE service_sale
(
    id                    SERIAL PRIMARY KEY,
    specialist_service_id INT REFERENCES specialist_service (id) ON DELETE CASCADE NOT NULL,
    address_id            INT REFERENCES address (id) ON DELETE CASCADE            NOT NULL,
    start_day             DATE                                                     NOT NULL,
    duration_days         INT                                                      NOT NULL,
    sale_price            INT                                                      NOT NULL
);