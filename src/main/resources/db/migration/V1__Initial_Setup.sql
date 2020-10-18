CREATE TABLE category (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(255) NOT NULL,
    type varchar(255) NOT NULL
);

CREATE TABLE transaction (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount double precision NOT NULL,
    created_at timestamp NULL,
    "date" date NOT NULL,
    description varchar(255) NULL,
    updated_at timestamp NULL,
    category_id bigint REFERENCES category(id)
);

CREATE TABLE budget (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount_limit double precision,
    created_at timestamp without time zone,
    period_type varchar(20),
    updated_at timestamp without time zone,
    category_id bigint REFERENCES category(id)
);
