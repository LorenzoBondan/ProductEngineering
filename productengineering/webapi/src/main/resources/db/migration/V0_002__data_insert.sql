-- Inserir roles e retornar seus IDs
WITH new_roles AS (
    INSERT INTO tb_role (authority)
        VALUES
            ('ROLE_OPERATOR'),
            ('ROLE_ANALYST'),
            ('ROLE_ADMIN')
        RETURNING id, authority
),

-- Inserir usuários e retornar seus IDs
     new_users AS (
         INSERT INTO tb_user (name, email, password, img_url, status)
             VALUES
                 ('Alex', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/47.jpg', 'ACTIVE'),
                 ('Maria', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://i.pinimg.com/originals/76/ef/b7/76efb7c94755748d695d3d46cf11d08d.jpg', 'ACTIVE'),
                 ('Bob', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/62.jpg', 'ACTIVE')
             RETURNING id, name
     )

-- Inserir as relações entre os usuários e os roles
INSERT INTO tb_user_role (user_id, role_id)
SELECT
    u.id AS user_id,
    r.id AS role_id
FROM new_users u
         JOIN new_roles r ON
    (u.name = 'Alex' AND r.authority IN ('ROLE_OPERATOR', 'ROLE_ANALYST'))
        OR (u.name = 'Maria' AND r.authority IN ('ROLE_OPERATOR', 'ROLE_ANALYST', 'ROLE_ADMIN'))
        OR (u.name = 'Bob' AND r.authority IN ('ROLE_OPERATOR', 'ROLE_ANALYST'));

-- Inserir cores
INSERT INTO tb_color (code, name, status)
VALUES
    (307, 'MINERALE', 'ACTIVE'),
    (302, 'PRISMA', 'ACTIVE'),
    (308, 'STONE', 'ACTIVE'),
    (999, 'BRANCA', 'ACTIVE');

-- Inserir materiais
WITH new_materials AS (
    INSERT INTO tb_material (name, status)
        VALUES
            ('MDP', 'ACTIVE'),
            ('MDF', 'ACTIVE')
        RETURNING id, name
)

-- Inserir chapas
INSERT INTO tb_sheet (code, description, thickness, family, implementation, faces, lost_percentage, color_id, material_id, status, value)
VALUES
    (1022602, 'MDP BP 18MM - MINERALE/CZ COBALTO 2F', 18.0, '18MDPCHA', null, 2, 13, 307, (SELECT id FROM new_materials WHERE name = 'MDP'), 'ACTIVE', 10.0),
    (1022610, 'MDP BP 18MM - PRISMA 2F', 18.0, '18MDPCHA', null, 2, 13, 302, (SELECT id FROM new_materials WHERE name = 'MDP'), 'ACTIVE', 20.0),
    (1022618, 'MDP BP 18MM - STONE/CHUMBO 2F', 18.0, '18MDPCHA', null, 2, 13, 308, (SELECT id FROM new_materials WHERE name = 'MDP'), 'ACTIVE', 30.0),
    (10, 'MDF BP 18 MM 2F', 18, '18MDFCHA', null, 2, 10 , 999, (SELECT id FROM new_materials WHERE name = 'MDF'), 'ACTIVE', 50.0),
    (11, 'MDF BP 18 MM 1F', 18, '18MDFCHA', null, 1, 10 , 999, (SELECT id FROM new_materials WHERE name = 'MDF'), 'ACTIVE', 50.0);

-- Inserir fitas de borda
INSERT INTO tb_edge_banding (code, description, height, thickness, family, implementation, lost_percentage, color_id, status, value)
VALUES
    (1022604, 'FITA BORDA PS 2107 - MINERALE/CZ COBALTO', 21, 7, '11FITABO', null, 8.6, 307, 'ACTIVE', 30.0),
    (1022613, 'FITA BORDA PS 2107 - PRISMA', 21, 7, '11FITABO', null, 8.6, 302, 'ACTIVE', 40.0),
    (1022620, 'FITA BORDA PS 2107 - STONE/CHUMBO', 21, 7, '11FITABO', null, 8.6, 308, 'ACTIVE', 50.0);

-- Inserir colas
INSERT INTO tb_glue (code, description, grammage, lost_percentage, status, value)
VALUES
    (1025359, 'COLA PUR CQ 645', 0.09, 12, 'ACTIVE', 2.0);

-- Inserir cantoneiras
INSERT INTO tb_corner_bracket (code, description, status, value)
VALUES
    (111111, 'CORNER BRACKET 1', 'ACTIVE', 0.75);

-- Inserir não-tecidos
INSERT INTO tb_nonwoven_fabric (code, description, lost_percentage, status, value)
VALUES
    (222222, 'NONWOVEN FABRIC 1', 10.0, 'ACTIVE', 2.0);

-- Inserir plásticos
INSERT INTO tb_plastic (code, description, grammage, lost_percentage, status, value)
VALUES
    (333333, 'PLASTIC 1', 0.5, 10, 'ACTIVE', 3.0);

-- Inserir polietilenos
INSERT INTO tb_polyethylene (code, description, lost_percentage, status, value)
VALUES
    (444444, 'POLYETHYLENE 1', 10, 'ACTIVE', 5.0);

-- Inserir tipos de pintura
WITH new_painting_types AS (
    INSERT INTO tb_painting_type (description, status)
        VALUES
            ('Satin', 'ACTIVE'),
            ('High Shine', 'ACTIVE'),
            ('Satin Glass', 'ACTIVE')
        RETURNING id, description
)

-- Inserir pinturas
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, color_id, status, value)
VALUES
    (201, 'MINERALE ACETINADA', 10, (SELECT id FROM new_painting_types WHERE description = 'Satin'), 307, 'ACTIVE', 12.0),
    (202, 'PRISMA ACETINADA', 10, (SELECT id FROM new_painting_types WHERE description = 'Satin'), 302, 'ACTIVE', 13.0),
    (203, 'STONE ACETINADA', 10, (SELECT id FROM new_painting_types WHERE description = 'Satin'), 308, 'ACTIVE', 14.0),
    (204, 'MINERALE ALTO BRILHO', 10, (SELECT id FROM new_painting_types WHERE description = 'High Shine'), 307, 'ACTIVE', 15.0),
    (205, 'PRISMA ALTO BRILHO', 10, (SELECT id FROM new_painting_types WHERE description = 'High Shine'), 302, 'ACTIVE', 16.0),
    (206, 'STONE ALTO BRILHO', 10, (SELECT id FROM new_painting_types WHERE description = 'High Shine'), 308, 'ACTIVE', 17.0);

-- Inserir bordas de pintura
INSERT INTO tb_painting_border_background (code, description, lost_percentage, status, value)
VALUES
    (30, 'Standard Painting Border', 10, 'ACTIVE', 18.0);

-- Inserir poliéster
INSERT INTO tb_polyester (code, description, lost_percentage, status, value)
VALUES
    (40, 'Standard Polyester', 10, 'ACTIVE', 50.0);

-- Inserir itens
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value)
VALUES
    (1019262, 'ACAB FTE GAV ALUM TRANSLUCIDO 810 MM', 810, 16, 16, 'Attachment', 'ACTIVE', 50.0),
    (70002299, 'ACESSÓRIO GAVETA ALUM H-90 P-500', 500, 90, 18, 'Attachment', 'ACTIVE', 50.0),
    (888, 'PUXADOR ALUMINIO', 192, 35, 5, 'DrawerPull', 'ACTIVE', 30.0),
    (155687, 'PERFIL BAGUETE 123', null, null, null, 'Molding', 'ACTIVE', 20.0),
    (158963, 'PARAFUSO 18 MM BRANCO', null, null, null, 'Screw', 'ACTIVE', 2.0),
    (158964, 'PARAFUSO 18 MM PRETO', null, null, null, 'Screw', 'ACTIVE', 2.0),
    (1693258, 'TRY SQUARE 3456', null, null, null, 'TrySquare', 'ACTIVE', 5.0);

-- Inserir vidros
INSERT INTO tb_item (code, description, measure1, measure2, measure3, color_id, dtype, status, value)
VALUES
    (10108000, 'VIDRO TEMPERADO MINERALE', 2205, 581, 18, 307, 'Glass', 'ACTIVE', 400.0),
    (10108001, 'VIDRO TEMPERADO PRISMA', 2205, 581, 18, 302, 'Glass', 'ACTIVE', 400.0);

-- Inserir grupos de máquinas
WITH new_machine_groups AS (
    INSERT INTO tb_machine_group (name, status)
        VALUES
            ('CNCs', 'ACTIVE')
        RETURNING id, name
)

-- Inserir máquinas
INSERT INTO tb_machine (name, formula, machine_group_id, value, status)
VALUES
    ('CNC BIMA', ARRAY['(', 'x', '+', 'y', ')', '/', '1000', '*', '2'], (SELECT id FROM new_machine_groups WHERE name = 'CNCs'), 2.0, 'ACTIVE');
