CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(80) NOT NULL,
    email    VARCHAR(50) UNIQUE
);
