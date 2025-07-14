CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE IF NOT EXISTS public.tb_role
(
    id        integer generated always as identity primary key,
    authority varchar(255) not null
);

CREATE TABLE IF NOT EXISTS public.tb_user
(
    id            integer generated always as identity primary key,
    name          varchar(50)                 not null,
    email         varchar(50)                 not null unique,
    password      varchar(200)                not null
);

CREATE TABLE IF NOT EXISTS public.tb_user_role
(
    user_id integer not null references public.tb_user (id),
    role_id integer not null references public.tb_role (id),
    primary key (user_id, role_id)
);

INSERT INTO tb_user (id, email, name, password) OVERRIDING SYSTEM VALUE VALUES (1,'alex@gmail.com', 'Alex', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (id, email, name, password) OVERRIDING SYSTEM VALUE VALUES (2,'maria@gmail.com', 'Maria', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
INSERT INTO tb_user (id, email, name, password) OVERRIDING SYSTEM VALUE VALUES (3,'bob@gmail.com', 'Bob', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG');
SELECT setval('tb_user_id_seq', 3);

INSERT INTO tb_role (id, authority) OVERRIDING SYSTEM VALUE VALUES (1,'ROLE_OPERATOR');
INSERT INTO tb_role (id, authority) OVERRIDING SYSTEM VALUE VALUES (2,'ROLE_ANALYST');
INSERT INTO tb_role (id, authority) OVERRIDING SYSTEM VALUE VALUES (3,'ROLE_ADMIN');
SELECT setval('tb_role_id_seq', 3);

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);