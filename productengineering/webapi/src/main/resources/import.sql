INSERT INTO tb_user (name, email, password, img_Url, status) VALUES ('Alex', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/47.jpg', 'ACTIVE');
INSERT INTO tb_user (name, email, password, img_Url, status) VALUES ('Maria', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://i.pinimg.com/originals/76/ef/b7/76efb7c94755748d695d3d46cf11d08d.jpg', 'ACTIVE');
INSERT INTO tb_user (name, email, password, img_Url, status) VALUES ('Bob', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/62.jpg', 'ACTIVE');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ANALYST');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);

INSERT INTO tb_color (code, name, status) VALUES (307, 'MINERALE', 'ACTIVE');
INSERT INTO tb_color (code, name, status) VALUES (302, 'PRISMA', 'ACTIVE');
INSERT INTO tb_color (code, name, status) VALUES (308, 'STONE', 'ACTIVE');
INSERT INTO tb_color (code, name, status) VALUES (999, 'BRANCA', 'ACTIVE');

INSERT INTO tb_material (name, status) VALUES ('MDP', 'ACTIVE');
INSERT INTO tb_material (name, status) VALUES ('MDF', 'ACTIVE');

INSERT INTO tb_sheet (code, description, thickness, family, implementation, faces, lost_percentage, color_id, material_id, status, value) VALUES (1022602, 'MDP BP 18MM - MINERALE/CZ COBALTO 2F', 18.0, '18MDPCHA', null, 2, 13, 307, 1L, 'ACTIVE', 10.0);
INSERT INTO tb_sheet (code, description, thickness, family, implementation, faces, lost_percentage, color_id, material_id, status, value) VALUES (1022610, 'MDP BP 18MM - PRISMA 2F', 18.0, '18MDPCHA', null, 2, 13, 302, 1L, 'ACTIVE', 20.0);
INSERT INTO tb_sheet (code, description, thickness, family, implementation, faces, lost_percentage, color_id, material_id, status, value) VALUES (1022618, 'MDP BP 18MM - STONE/CHUMBO 2F', 18.0, '18MDPCHA', null, 2, 13, 308, 1L, 'ACTIVE', 30.0);
INSERT INTO tb_sheet (code, description, thickness, family, implementation, faces, lost_percentage, color_id, material_id, status, value) VALUES (10, 'MDF BP 18 MM 2F', 18, '18MDFCHA', null, 2, 10 , 999, 2L, 'ACTIVE', 50.0);
INSERT INTO tb_sheet (code, description, thickness, family, implementation, faces, lost_percentage, color_id, material_id, status, value) VALUES (11, 'MDF BP 18 MM 1F', 18, '18MDFCHA', null, 1, 10 , 999, 2L, 'ACTIVE', 50.0);

INSERT INTO tb_edge_banding (code, description, height, thickness, family, implementation, lost_percentage, color_id, status, value) VALUES (1022604, 'FITA BORDA PS 2107 - MINERALE/CZ COBALTO', 21, 7, '11FITABO', null, 8.6, 307, 'ACTIVE', 30.0);
INSERT INTO tb_edge_banding (code, description, height, thickness, family, implementation, lost_percentage, color_id, status, value) VALUES (1022613, 'FITA BORDA PS 2107 - PRISMA', 21, 7, '11FITABO', null, 8.6, 302, 'ACTIVE', 40.0);
INSERT INTO tb_edge_banding (code, description, height, thickness, family, implementation, lost_percentage, color_id, status, value) VALUES (1022620, 'FITA BORDA PS 2107 - STONE/CHUMBO', 21, 7, '11FITABO', null, 8.6, 308, 'ACTIVE', 50.0);

INSERT INTO tb_glue (code, description, grammage, lost_percentage, status, value) VALUES (1025359,	'COLA PUR CQ 645', 0.09, 12, 'ACTIVE', 2.0);

INSERT INTO tb_corner_bracket (code, description, status, value) VALUES (111111, 'CORNER BRACKET 1', 'ACTIVE', 0.75);

INSERT INTO tb_nonwoven_fabric (code, description, lost_percentage, status, value) VALUES (222222, 'NONWOVEN FABRIC 1', 10.0, 'ACTIVE', 2.0);

INSERT INTO tb_plastic (code, description, grammage, lost_percentage, status, value) VALUES (333333, 'PLASTIC 1', 0.5, 10, 'ACTIVE', 3.0);

INSERT INTO tb_polyethylene (code, description, lost_percentage, status, value) VALUES (444444, 'POLYETHYLENE 1', 10, 'ACTIVE', 5.0);

INSERT INTO tb_painting_type (description, status) VALUES ('Satin', 'ACTIVE');
INSERT INTO tb_painting_type (description, status) VALUES ('High Shine', 'ACTIVE');
INSERT INTO tb_painting_type (description, status) VALUES ('Satin Glass', 'ACTIVE');

INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, color_id, status, value) VALUES (201, 'MINERALE ACETINADA', 10, 1, 307, 'ACTIVE', 12.0);
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, color_id, status, value) VALUES (202, 'PRISMA ACETINADA', 10, 1, 302, 'ACTIVE', 13.0);
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, color_id, status, value) VALUES (203, 'STONE ACETINADA', 10, 1, 308, 'ACTIVE', 14.0);

INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, color_id, status, value) VALUES (204, 'MINERALE ALTO BRILHO', 10, 2, 307, 'ACTIVE', 15.0);
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, color_id, status, value) VALUES (205, 'PRISMA ALTO BRILHO', 10, 2, 302, 'ACTIVE', 16.0);
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, color_id, status, value) VALUES (206, 'STONE ALTO BRILHO', 10, 2, 308, 'ACTIVE', 17.0);

INSERT INTO tb_painting_border_background (code, description, lost_percentage, status, value) VALUES (30, 'Standard Painting Border', 10, 'ACTIVE', 18.0);

INSERT INTO tb_polyester (code, description, lost_percentage, status, value) VALUES (40, 'Standard Polyester', 10, 'ACTIVE', 50.0);

-- Attachments
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value) VALUES (1019262, 'ACAB FTE GAV ALUM TRANSLUCIDO 810 MM', 810, 16, 16, 'Attachment', 'ACTIVE', 50.0);
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value) VALUES (70002299, 'ACESSÓRIO GAVETA ALUM H-90 P-500', 500, 90, 18, 'Attachment', 'ACTIVE', 50.0);

INSERT INTO tb_aluminium_type (name, less_quantity, status) VALUES ('MOCCA', 0.5, 'ACTIVE');

-- DrawerPull
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value) VALUES (888, 'PUXADOR ALUMINIO', 192, 35, 5, 'DrawerPull', 'ACTIVE', 30.0);

-- Glass
INSERT INTO tb_item (code, description, measure1, measure2, measure3, color_id, dtype, status, value) VALUES (10108000, 'VIDRO TEMPERADO MINERALE', 2205, 581, 18, 307, 'Glass', 'ACTIVE', 400.0);
INSERT INTO tb_item (code, description, measure1, measure2, measure3, color_id, dtype, status, value) VALUES (10108001, 'VIDRO TEMPERADO PRISMA', 2205, 581, 18, 302, 'Glass', 'ACTIVE', 400.0);

-- Molding
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value) VALUES (155687, 'PERFIL BAGUETE 123', null, null, null, 'Molding', 'ACTIVE', 20.0);

-- Screw
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value) VALUES (158963, 'PARAFUSO 18 MM BRANCO', null, null, null, 'Screw', 'ACTIVE', 2.0);
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value) VALUES (158964, 'PARAFUSO 18 MM PRETO', null, null, null, 'Screw', 'ACTIVE', 2.0);

-- TrySquare
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status, value) VALUES (1693258, 'TRY SQUARE 3456', null, null, null, 'TrySquare', 'ACTIVE', 5.0);

-- MachineGroup
INSERT INTO tb_machine_group (name, status) VALUES ('CNCs', 'ACTIVE');
