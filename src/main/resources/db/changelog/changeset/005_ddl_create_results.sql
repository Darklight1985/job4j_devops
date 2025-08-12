--liquibase formatted sql
--changeset kolesnev:create_calc_events
CREATE TABLE calc_events (
                         id SERIAL PRIMARY KEY,
                         first_arg DECIMAL,
                         second_arg DECIMAL,
                         result DECIMAL,
                         user_id bigint,
                         type TEXT,
                         create_date TIMESTAMP WITHOUT TIME ZONE DEFAULT now()
);

--rollback DROP TABLE calc_events;