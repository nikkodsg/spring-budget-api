CREATE TABLE app_user (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL
);