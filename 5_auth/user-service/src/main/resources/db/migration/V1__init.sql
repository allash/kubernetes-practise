CREATE TABLE "public"."user_address" (
    id BIGINT NOT NULL,
    street VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    user_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT user_pk PRIMARY KEY (id)
);

CREATE SEQUENCE user_address_id_seq START WITH 1 INCREMENT BY 1;
