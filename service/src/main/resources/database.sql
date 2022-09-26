CREATE TABLE specialist
(
    id SERIAL PRIMARY KEY ,
    email VARCHAR(126) UNIQUE NOT NULL ,
    first_name VARCHAR(126) NOT NULL,
    last_name VARCHAR(126) NOT NULL,
    gender VARCHAR(32) NOT NULL,
    birthday TIMESTAMP NOT NULL
);

CREATE TABLE client
(
    id SERIAL PRIMARY KEY ,
    email VARCHAR(126) UNIQUE NOT NULL ,
    first_name VARCHAR(126) NOT NULL,
    last_name VARCHAR(126) NOT NULL,
    gender VARCHAR(32) NOT NULL,
    birthday TIMESTAMP NOT NULL
);

CREATE TABLE administrator
(
    id SERIAL PRIMARY KEY ,
    email VARCHAR(126) UNIQUE NOT NULL ,
    first_name VARCHAR(126) NOT NULL,
    last_name VARCHAR(126) NOT NULL,
    gender VARCHAR(32) NOT NULL,
    birthday TIMESTAMP NOT NULL
);

CREATE TABLE appointment
(
    id SERIAL PRIMARY KEY ,
    client_id INT REFERENCES client(id),
    specialist_id INT REFERENCES specialist(id),
    date DATE NOT NULL ,
    start_time TIME NOT NULL ,
    length_min INT NOT NULL ,
    massage_type VARCHAR(32) NOT NULL
);