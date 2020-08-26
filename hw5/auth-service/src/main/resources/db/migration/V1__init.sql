CREATE TABLE "public"."user" (
    id BIGINT NOT NULL,
    username VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    phone VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE SEQUENCE user_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "public"."session" (
    id BIGINT NOT NULL,
    token VARCHAR NOT NULL,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE SEQUENCE session_id_seq START WITH 1 INCREMENT BY 1;