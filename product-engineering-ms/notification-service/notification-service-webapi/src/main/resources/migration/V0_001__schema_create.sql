CREATE TABLE IF NOT EXISTS public.tb_notification
(
    id        integer generated always as identity primary key,
    user_id   integer not null,
    message   varchar(255) not null,
    sent_date timestamp with time zone default now() not null,
    is_read   boolean default false not null
);