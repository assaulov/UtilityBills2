CREATE SEQUENCE public.t_users_user_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE t_users (
    user_id bigint NOT NULL DEFAULT nextval('t_users_user_id_seq'::regclass),
    login varchar(255) NOT NULL,
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255) NOT NULL,
    gender varchar(255),
    email varchar(255)
)