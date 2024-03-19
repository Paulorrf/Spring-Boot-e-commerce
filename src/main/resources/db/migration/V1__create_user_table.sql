create table users(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    avatar TEXT,
    role VARCHAR(255),
    identifier VARCHAR(255)
);