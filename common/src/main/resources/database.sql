CREATE SCHEMA IF NOT EXISTS "cooking";

SET search_path TO cooking, public;

CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    email    VARCHAR(128) UNIQUE NOT NULL,
    username VARCHAR(128) UNIQUE NOT NULL,
    password VARCHAR(128)        NOT NULL,
    is_admin BOOLEAN             NOT NULL
    );

CREATE TABLE IF NOT EXISTS user_details
(
    user_id    INTEGER REFERENCES users (id) ON DELETE CASCADE PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    birthday   DATE         NOT NULL,
    image      VARCHAR(124) DEFAULT 'resources/img/defaultUserImage.jpg'
    );

CREATE TABLE IF NOT EXISTS ingredient
(
    id                       SERIAL PRIMARY KEY,
    name                     VARCHAR(128) UNIQUE NOT NULL,
    weight_of_one_piece_gram INTEGER             NOT NULL,
    kilocalories             INTEGER             NOT NULL,
    protein_gr               REAL                NOT NULL,
    fat_gr                   REAL                NOT NULL,
    carbohydrate_gr          REAL                NOT NULL
    );

CREATE TABLE IF NOT EXISTS spice
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS recipe
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS recipe_detail
(
    recipe_id                  INTEGER REFERENCES recipe (id) ON DELETE CASCADE PRIMARY KEY,
    user_id                    INTEGER REFERENCES users (id) ON DELETE SET NULL,
    description                TEXT    NOT NULL,
    active_cooking_time_minute INTEGER,
    total_cooking_time_minute  INTEGER,
    number_of_portions         INTEGER
    );

CREATE TABLE IF NOT EXISTS recipe_ingredient
(
    recipe_id     INTEGER REFERENCES recipe (id) ON DELETE CASCADE,
    ingredient_id INTEGER REFERENCES ingredient (id),
    gram          INTEGER,
    notes         VARCHAR(128),
    PRIMARY KEY (recipe_id, ingredient_id)
    );

CREATE TABLE IF NOT EXISTS recipe_spice
(
    recipe_id INTEGER REFERENCES recipe (id) ON DELETE CASCADE,
    spice_id  INTEGER REFERENCES spice (id),
    notes     VARCHAR(128),
    PRIMARY KEY (recipe_id, spice_id)
    );

CREATE TABLE IF NOT EXISTS recipe_recipe
(
    recipe_id      INTEGER REFERENCES recipe (id) ON DELETE CASCADE,
    used_recipe_id INTEGER REFERENCES recipe (id) ON DELETE SET NULL,
    gram           INTEGER,
    notes          VARCHAR(128),
    PRIMARY KEY (recipe_id, used_recipe_id)
    );

CREATE TABLE IF NOT EXISTS user_favorite_recipes
(
    user_id   INTEGER REFERENCES users (id) ON DELETE CASCADE,
    recipe_id INTEGER REFERENCES recipe (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, recipe_id)
    );

CREATE TABLE IF NOT EXISTS meal_plan
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(128) NOT NULL,
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE,
    UNIQUE (user_id, name)
    );

CREATE TABLE IF NOT EXISTS meal_plan_recipe
(
    id           SERIAL PRIMARY KEY,
    meal_plan_id INTEGER REFERENCES meal_plan (id) ON DELETE CASCADE,
    day_number   VARCHAR(128) NOT NULL,
    recipe_id    INTEGER     REFERENCES recipe (id) ON DELETE SET NULL,
    meal_type    VARCHAR(64) NOT NULL
    );