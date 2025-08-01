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
INSERT INTO tb_medidas (cdmedidas, altura, largura, espessura) OVERRIDING SYSTEM VALUE VALUES (2,353,120,300);
INSERT INTO tb_medidas (cdmedidas, altura, largura, espessura) OVERRIDING SYSTEM VALUE VALUES (3,300,120,18);
INSERT INTO tb_medidas (cdmedidas, altura, largura, espessura) OVERRIDING SYSTEM VALUE VALUES (4,307,104,18);
SELECT setval('tb_medidas_cdmedidas_seq', 4);

-- Inserir grupos de máquinas
INSERT INTO tb_grupo_maquina (cdgrupo_maquina, nome) OVERRIDING SYSTEM VALUE VALUES (1, 'CNCs');
SELECT setval('tb_grupo_maquina_cdgrupo_maquina_seq', 1);

-- Inserir máquinas
INSERT INTO tb_maquina (cdmaquina, nome, formula, valor, cdgrupo_maquina) OVERRIDING SYSTEM VALUE VALUES (1,'CNC BIMA', '(M1+M2) / 1000 * 2', 50.0, 1);
SELECT setval('tb_maquina_cdmaquina_seq', 1);

-- Inserir roteiros
INSERT INTO tb_roteiro (cdroteiro, descricao, implantacao, data_final, valor) OVERRIDING SYSTEM VALUE VALUES (1, 'Não informado', '0001-01-01', '0001-01-01', 0);
SELECT setval('tb_roteiro_cdroteiro_seq', 1);

-- Inserir materiais
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) OVERRIDING SYSTEM VALUE VALUES (1, 'Chapa', 'MDP BP 18MM - MINERALE CZ COBALTO 2F', 1, '2024-01-01', 13, 10, 1, 18, 2, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) OVERRIDING SYSTEM VALUE VALUES (2, 'Chapa', 'MDP BP 18MM - PRISMA 2F', 1, '2024-01-01', 13, 10, 2, 18, 2, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) OVERRIDING SYSTEM VALUE VALUES (3, 'Chapa', 'MDP BP 18MM - STONE CHUMBO 2F', 1, '2024-01-01', 13, 10, 3, 18, 2, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) OVERRIDING SYSTEM VALUE VALUES (4, 'Chapa', 'MDF BP 18 MM 2F', 2, '2024-01-01', 10, 20, 4, 18, 2, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, cdcor, espessura, faces, situacao) OVERRIDING SYSTEM VALUE VALUES (5, 'Chapa', 'MDF BP 18 MM 1F', 2, '2024-01-01', 10, 20, 4, 18, 1, 'ATIVO');

INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, altura, espessura, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (6, 'FitaBorda', 'FITA BORDA PS 2107 - MINERALE CZ COBALTO', 3, '2024-01-01', 8.6, 30, 21, 7, 1,'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, altura, espessura, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (7, 'FitaBorda', 'FITA BORDA PS 2107 - PRISMA', 3, '2024-01-01', 8.6, 30, 21, 7, 2,'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, altura, espessura, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (8, 'FitaBorda', 'FITA BORDA PS 2107 - STONE CHUMBO', 3, '2024-01-01', 8.6, 30, 21, 7, 3,'ATIVO');

INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, gramatura, situacao) OVERRIDING SYSTEM VALUE VALUES (9, 'Cola', 'COLA PUR CQ 645', 4, '2024-01-01', 12, 15.0, 0.09, 'ATIVO');

INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (10, 'Cantoneira', 'Cantoneira 2MM', 5, '2024-01-01', 0, 5.0, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (11, 'Tnt', 'TNT 1', 6, '2024-01-01', 0, 25.0, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (12, 'Polietileno', 'Polietileno 1', 7, '2024-01-01', 15, 50.0, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, gramatura, situacao) OVERRIDING SYSTEM VALUE VALUES (13, 'Plastico', 'Plastico 1', 8, '2024-01-01', 15, 50.0, 0.5, 'ATIVO');

INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, tipo_pintura, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (14, 'Pintura', 'Pintura Acetinada Minerale', 9, '2024-01-01', 12, 75.0, 1, 1, 'ATIVO');
INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, tipo_pintura, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (15, 'Pintura', 'Pintura Acetinada Prisma', 9, '2024-01-01', 12, 75.0, 1, 2, 'ATIVO');

INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (16, 'PinturaBordaFundo', 'Pintura de Borda de Fundo Padrão', 10, '2024-01-01', 10, 80.0, 'ATIVO');

INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (17, 'Poliester', 'Poliester Padrão', 11, '2024-01-01', 20, 100.0, 'ATIVO');

INSERT INTO tb_material (cdmaterial, dtype, descricao, tipo_material, implantacao, porcentagem_perda, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (18, 'Baguete', 'Baguete Padrão', 12, '2024-01-01', 20, 100.0, 'ATIVO');
SELECT setval('tb_material_cdmaterial_seq', 18);

-- Inserir acessórios
INSERT INTO tb_acessorio (cdacessorio, descricao, cdmedidas, implantacao, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (1, 'ACAB FTE GAV ALUM TRANSLUCIDO 810 MM', 1, '2024-01-01',  50.0, 'ATIVO');
INSERT INTO tb_acessorio (cdacessorio, descricao, cdmedidas, implantacao, valor, situacao) OVERRIDING SYSTEM VALUE VALUES (2, 'ACESSÓRIO GAVETA ALUM H-90 P-500', 1, '2024-01-01', 50.0, 'ATIVO');

INSERT INTO tb_acessorio (cdacessorio, descricao, cdmedidas, implantacao, valor, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (3,'VIDRO TEMPERADO MINERALE', 1, '2024-01-01', 200.0, 1, 'ATIVO');
INSERT INTO tb_acessorio (cdacessorio, descricao, cdmedidas, implantacao, valor, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (4,'VIDRO TEMPERADO PRISMA', 1, '2024-01-01', 200.0, 2, 'ATIVO');

INSERT INTO tb_acessorio (cdacessorio, descricao, cdmedidas, implantacao, valor, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (5,'PARAFUSO 18 MM BRANCO', 1, '2024-01-01', 2.0, 4, 'ATIVO');

INSERT INTO tb_acessorio (cdacessorio, descricao, cdmedidas, implantacao, valor, cdcor, situacao) OVERRIDING SYSTEM VALUE VALUES (6,'ESQUADRETA 123', 1, '2024-01-01', 50.0, 4, 'ATIVO');
SELECT setval('tb_acessorio_cdacessorio_seq', 6);

-- Inserir pai
INSERT INTO tb_pai (cdpai, cdmodelo, cdcategoria_componente, descricao, bordas_comprimento, bordas_largura, numero_cantoneiras, tnt_uma_face, plastico_acima, plastico_adicional, largura_plastico, faces, especial, tipo_pintura) OVERRIDING SYSTEM VALUE VALUES (1, 1, 1, 'Frente Teste', 1, 1, 4, false, true, 0, 400, 2, false, 1);
SELECT setval('tb_pai_cdpai_seq', 1);

-- Inserir filho
INSERT INTO tb_filho (cdfilho, descricao, cdpai, cdcor, cdmedidas, cdroteiro, unidade_medida, implantacao, valor, tipo) OVERRIDING SYSTEM VALUE VALUES (1, 'Teste', 1, 1, 1, 1, 'UN', null, null, 1);
SELECT setval('tb_filho_cdfilho_seq', 1);

-- Inserir material usado
INSERT INTO tb_material_usado (cdfilho, cdmaterial, valor, quantidade_liquida, quantidade_bruta, unidade_medida, dtype) VALUES (1,1, 10, 1, 1, 'M', 'ChapaUsada');
SELECT setval('tb_material_usado_cdmaterial_usado_seq', 1);