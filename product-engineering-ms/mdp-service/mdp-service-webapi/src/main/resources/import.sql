INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('MINERALE', 'F12345','ATIVO');
INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('PRISMA', 'F123FF','ATIVO');
INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('STONE', 'F12896','ATIVO');
INSERT INTO tb_cor (descricao, hexa, situacao) VALUES ('BRANCA', 'FFF','ATIVO');

INSERT INTO tb_medidas (altura, largura, espessura, situacao) VALUES (1,1,1, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, situacao) VALUES ('ACAB FTE GAV ALUM TRANSLUCIDO 810 MM', 1, '2024-01-01',  50.0, 'ATIVO');
INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, situacao) VALUES ('ACESSÓRIO GAVETA ALUM H-90 P-500', 1, '2024-01-01', 50.0, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('VIDRO TEMPERADO MINERALE', 1, '2024-01-01', 200.0, 1, 'ATIVO');
INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('VIDRO TEMPERADO PRISMA', 1, '2024-01-01', 200.0, 2, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('PARAFUSO 18 MM BRANCO', 1, '2024-01-01', 2.0, 4, 'ATIVO');

INSERT INTO tb_acessorio (descricao, cdmedidas, implantacao, valor, cdcor, situacao) VALUES ('ESQUADRETA 123', 1, '2024-01-01', 50.0, 4, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDP BP 18MM - MINERALE CZ COBALTO 2F', 1, '2024-01-01', 13, 10, 1, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDP BP 18MM - PRISMA 2F', 1, '2024-01-01', 13, 10, 2, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDP BP 18MM - STONE CHUMBO 2F', 1, '2024-01-01', 13, 10, 3, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDF BP 18 MM 2F', 2, '2024-01-01', 10, 20, 4, 18, 2, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) VALUES ('Chapa', 'MDF BP 18 MM 1F', 2, '2024-01-01', 10, 20, 4, 18, 1, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, altura, espessura, situacao) VALUES ('FitaBorda', 'FITA BORDA PS 2107 - MINERALE CZ COBALTO', 3, '2024-01-01', 8.6, 30, 1, 21, 7, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, altura, espessura, situacao) VALUES ('FitaBorda', 'FITA BORDA PS 2107 - PRISMA', 3, '2024-01-01', 8.6, 30, 2, 21, 7, 'ATIVO');
INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, altura, espessura, situacao) VALUES ('FitaBorda', 'FITA BORDA PS 2107 - STONE CHUMBO', 3, '2024-01-01', 8.6, 30, 3, 21, 7, 'ATIVO');

INSERT INTO tb_material (dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, gramatura, situacao) VALUES ('Cola', 'COLA PUR CQ 645', 4, '2024-01-01', 12, 15.0, 0.09, 'ATIVO');