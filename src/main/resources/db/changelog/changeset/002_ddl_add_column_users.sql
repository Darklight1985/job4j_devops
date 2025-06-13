--liquibase formatted sql
--changeset kolesnev:add_columns_users
ALTER TABLE public.users
add column if not exists first_args varchar(63),
add column if not exists second_args varchar(63),
add column result text;