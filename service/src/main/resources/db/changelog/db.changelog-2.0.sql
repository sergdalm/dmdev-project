--liquibase formatted sql

--changeset asergienko:1
ALTER TABLE massage.user_info
ADD COLUMN image VARCHAR(64)