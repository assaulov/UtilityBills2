CREATE SEQUENCE public.t_metering_meter_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

CREATE TABLE t_metering
(
    meter_id            bigint NOT NULL DEFAULT nextval('t_metering_meter_id_seq'::regclass),
    meter_date_of_write date,
    cold_water          double precision,
    hot_water           double precision,
    electricity         double precision,
    gas                 double precision,
    user_id             bigint,
    PRIMARY KEY (meter_id),
    CONSTRAINT fk_t_users
        FOREIGN KEY (user_id)
            REFERENCES t_users (user_id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);
