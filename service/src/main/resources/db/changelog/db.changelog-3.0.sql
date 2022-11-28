--liquibase formatted sql

--changeset asergienko:1
ALTER TABLE massage.users
    ADD COLUMN first_name VARCHAR(128) NOT NULL;

--changeset asergienko:2
ALTER TABLE massage.users
    ADD COLUMN last_name  VARCHAR(128) NOT NULL;

--changeset asergienko:3
ALTER TABLE massage.user_info
    DROP COLUMN first_name;

--changeset asergienko:4
ALTER TABLE massage.user_info
    DROP COLUMN last_name;