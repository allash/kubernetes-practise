CREATE TABLE "public"."billing_account" (
    id BIGINT NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    phone VARCHAR NOT NULL,
    user_id BIGINT NOT NULL,
    balance DECIMAL DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT account_pk PRIMARY KEY (id)
);

CREATE SEQUENCE billing_account_id_seq START WITH 1 INCREMENT BY 1;
