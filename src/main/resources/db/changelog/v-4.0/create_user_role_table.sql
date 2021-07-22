CREATE TABLE t_user_role (
                             user_id bigint NOT NULL,
                             roles varchar(255),
                             CONSTRAINT fk_t_users
                                 FOREIGN KEY (user_id)
                                     REFERENCES t_users(user_id)

)