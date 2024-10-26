-- Inserir roles
INSERT INTO public.tb_role (id, authority) OVERRIDING SYSTEM VALUE VALUES (1, 'ROLE_OPERATOR');
INSERT INTO public.tb_role (id, authority) OVERRIDING SYSTEM VALUE VALUES (2, 'ROLE_ANALYST');
INSERT INTO public.tb_role (id, authority) OVERRIDING SYSTEM VALUE VALUES (3, 'ROLE_ADMIN');
SELECT setval('tb_role_id_seq', 3);

-- Inserir usuários
INSERT INTO public.tb_user (id, name, email, password, img_url) OVERRIDING SYSTEM VALUE VALUES (1, 'Alex', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/47.jpg');
INSERT INTO public.tb_user (id, name, email, password, img_url) OVERRIDING SYSTEM VALUE VALUES (2, 'Maria', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://i.pinimg.com/originals/76/ef/b7/76efb7c94755748d695d3d46cf11d08d.jpg');
INSERT INTO public.tb_user (id, name, email, password, img_url) OVERRIDING SYSTEM VALUE VALUES (3, 'Bob', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/62.jpg');
SELECT setval('tb_user_id_seq', 3);

-- Inserir as relações entre os usuários e os roles
INSERT INTO tb_user_role (user_id, role_id) values (1,1);
INSERT INTO tb_user_role (user_id, role_id) values (2,1);
INSERT INTO tb_user_role (user_id, role_id) values (2,2);
INSERT INTO tb_user_role (user_id, role_id) values (2,3);
INSERT INTO tb_user_role (user_id, role_id) values (3,1);
INSERT INTO tb_user_role (user_id, role_id) values (3,2);

-- Inserir categoria componente
INSERT INTO tb_categoria_componente (cdcategoria_componente, descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (1,'Frente', 'ATIVO');
INSERT INTO tb_categoria_componente (cdcategoria_componente,descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (2,'Frente Gaveta', 'ATIVO');
INSERT INTO tb_categoria_componente (cdcategoria_componente,descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (3,'Lateral Esquerda Gaveta', 'ATIVO');
INSERT INTO tb_categoria_componente (cdcategoria_componente,descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (4,'Lateral Direita Gaveta', 'ATIVO');
INSERT INTO tb_categoria_componente (cdcategoria_componente,descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (5,'Fundo', 'ATIVO');
SELECT setval('tb_categoria_componente_cdcategoria_componente_seq', 5);

-- Inserir modelos
INSERT INTO tb_modelo (cdmodelo, descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (1,'Fresa', 'ATIVO');
INSERT INTO tb_modelo (cdmodelo, descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (2,'Falsa', 'ATIVO');
INSERT INTO tb_modelo (cdmodelo, descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (3,'Arch', 'ATIVO');
INSERT INTO tb_modelo (cdmodelo, descricao, situacao) OVERRIDING SYSTEM VALUE VALUES (4,'Piano', 'ATIVO');
SELECT setval('tb_modelo_cdmodelo_seq', 4);

-- Inserir cores
INSERT INTO tb_cor (cdcor, descricao, hexa) OVERRIDING SYSTEM VALUE VALUES (1, 'Minerale', 'F12398');
INSERT INTO tb_cor (cdcor, descricao, hexa) OVERRIDING SYSTEM VALUE VALUES (2, 'Prisma', 'ABCDE0');
INSERT INTO tb_cor (cdcor, descricao, hexa) OVERRIDING SYSTEM VALUE VALUES (3, 'Stone', 'CDE000');
INSERT INTO tb_cor (cdcor, descricao, hexa) OVERRIDING SYSTEM VALUE VALUES (4, 'Branca', 'FFF');
SELECT setval('tb_cor_cdcor_seq', 4);

-- Inserir medidas
INSERT INTO tb_medidas (cdmedidas, altura, largura, espessura) OVERRIDING SYSTEM VALUE VALUES (1,1,1,1);
SELECT setval('tb_medidas_cdmedidas_seq', 1);

-- Inserir grupos de máquinas
WITH new_machine_groups AS (
    INSERT INTO tb_grupo_maquina (nome)
        VALUES
            ('CNCs')
        RETURNING cdgrupo_maquina, nome
)

-- Inserir máquinas
INSERT INTO tb_maquina (nome, formula, valor, cdgrupo_maquina)
VALUES
    ('CNC BIMA', '(M1+M2) / 1000 * 2', 50.0, (SELECT cdgrupo_maquina FROM new_machine_groups WHERE nome = 'CNCs'));

-- Inserir roteiros
INSERT INTO tb_roteiro (descricao, implantacao, data_final, valor) VALUES ('Não informado', '0001-01-01', '0001-01-01', 0);

-- Inserir materiais
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDP BP 18MM - MINERALE CZ COBALTO 2F', 1, '2024-01-01', 13, 10, 1, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDP BP 18MM - PRISMA 2F', 1, '2024-01-01', 13, 10, 2, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDP BP 18MM - STONE CHUMBO 2F', 1, '2024-01-01', 13, 10, 3, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDF BP 18 MM 2F', 2, '2024-01-01', 10, 20, 4, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDF BP 18 MM 1F', 2, '2024-01-01', 10, 20, 4, 18, 1, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, altura, espessura, situacao) VALUES ('FitaBorda', 'FITA BORDA PS 2107 - MINERALE CZ COBALTO', 3, '2024-01-01', 8.6, 30, 1, 21, 7, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, altura, espessura, situacao) VALUES ('FitaBorda', 'FITA BORDA PS 2107 - PRISMA', 3, '2024-01-01', 8.6, 30, 2, 21, 7, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, altura, espessura, situacao) VALUES ('FitaBorda', 'FITA BORDA PS 2107 - STONE CHUMBO', 3, '2024-01-01', 8.6, 30, 3, 21, 7, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, gramatura, situacao) VALUES ('Cola', 'COLA PUR CQ 645', 4, '2024-01-01', 12, 15.0, 0.09, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) VALUES ('Cantoneira', 'Cantoneira 2MM', 5, '2024-01-01', 0, 5.0, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) VALUES ('Tnt', 'TNT 1', 6, '2024-01-01', 0, 25.0, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) VALUES ('Polietileno', 'Polietileno 1', 7, '2024-01-01', 15, 50.0, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, gramatura, situacao) VALUES ('Plastico', 'Plastico 1', 8, '2024-01-01', 15, 50.0, 0.5, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, tipo_pintura, cdcor, situacao) VALUES ('Pintura', 'Pintura Acetinada Minerale', 9, '2024-01-01', 12, 75.0, 1, 1, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, tipo_pintura, cdcor, situacao) VALUES ('Pintura', 'Pintura Acetinada Prisma', 9, '2024-01-01', 12, 75.0, 1, 2, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) VALUES ('PinturaBordaFundo', 'Pintura de Borda de Fundo Padrão', 10, '2024-01-01', 10, 80.0, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) VALUES ('Poliester', 'Poliester Padrão', 11, '2024-01-01', 20, 100.0, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, situacao) VALUES ('ACAB FTE GAV ALUM TRANSLUCIDO 810 MM', 1, '2024-01-01',  50.0, 'ATIVO');
INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, situacao) VALUES ('ACESSÓRIO GAVETA ALUM H-90 P-500', 1, '2024-01-01', 50.0, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('VIDRO TEMPERADO MINERALE', 1, '2024-01-01', 200.0, 1, 'ATIVO');
INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('VIDRO TEMPERADO PRISMA', 1, '2024-01-01', 200.0, 2, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('PARAFUSO 18 MM BRANCO', 1, '2024-01-01', 2.0, 4, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('ESQUADRETA 123', 1, '2024-01-01', 50.0, 4, 'ATIVO');

