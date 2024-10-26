INSERT INTO tb_user (name, email, password, img_Url, situacao) VALUES ('Alex', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/47.jpg', 'ATIVO');
INSERT INTO tb_user (name, email, password, img_Url, situacao) VALUES ('Maria', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://i.pinimg.com/originals/76/ef/b7/76efb7c94755748d695d3d46cf11d08d.jpg', 'ATIVO');
INSERT INTO tb_user (name, email, password, img_Url, situacao) VALUES ('Bob', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/62.jpg', 'ATIVO');

INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');
INSERT INTO tb_role (authority) VALUES ('ROLE_ANALYST');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 2);

INSERT INTO tb_categoria_componente (descricao, situacao) VALUES ('Frente', 'ATIVO');
INSERT INTO tb_categoria_componente (descricao, situacao) VALUES ('Frente Gaveta', 'ATIVO');
INSERT INTO tb_categoria_componente (descricao, situacao) VALUES ('Lateral Esquerda Gaveta', 'ATIVO');
INSERT INTO tb_categoria_componente (descricao, situacao) VALUES ('Lateral Direita Gaveta', 'ATIVO');
INSERT INTO tb_categoria_componente (descricao, situacao) VALUES ('Fundo', 'ATIVO');

INSERT INTO tb_modelo (descricao, situacao) VALUES ('Fresa', 'ATIVO');
INSERT INTO tb_modelo (descricao, situacao) VALUES ('Falsa', 'ATIVO');
INSERT INTO tb_modelo (descricao, situacao) VALUES ('Arch', 'ATIVO');
INSERT INTO tb_modelo (descricao, situacao) VALUES ('Piano', 'ATIVO');

INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('MINERALE', 'F12345','ATIVO');
INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('PRISMA', 'F123FF','ATIVO');
INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('STONE', 'F12896','ATIVO');
INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('BRANCA', 'FFF','ATIVO');

INSERT INTO tb_grupo_maquina (nome, situacao) VALUES ('CNCs', 'ATIVO');

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

INSERT INTO tb_medidas (altura, largura, espessura, situacao) VALUES (1,1,1, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, situacao) VALUES ('ACAB FTE GAV ALUM TRANSLUCIDO 810 MM', 1, '2024-01-01',  50.0, 'ATIVO');
INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, situacao) VALUES ('ACESSÓRIO GAVETA ALUM H-90 P-500', 1, '2024-01-01', 50.0, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('VIDRO TEMPERADO MINERALE', 1, '2024-01-01', 200.0, 1, 'ATIVO');
INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('VIDRO TEMPERADO PRISMA', 1, '2024-01-01', 200.0, 2, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('PARAFUSO 18 MM BRANCO', 1, '2024-01-01', 2.0, 4, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('ESQUADRETA 123', 1, '2024-01-01', 50.0, 4, 'ATIVO');

