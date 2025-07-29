CREATE EXTENSION IF NOT EXISTS unaccent;

CREATE TABLE IF NOT EXISTS public.tb_cor
(
    cdcor         integer generated always as identity primary key,
    descricao     varchar(50)                 not null,
    hexa          varchar(10),
    criadopor     character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem      timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao      character varying(8)        NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_modelo
(
    cdmodelo      integer generated always as identity primary key,
    descricao     varchar(50)                 not null,
    criadopor     character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem      timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao      character varying(8)        NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_categoria_componente
(
    cdcategoria_componente integer generated always as identity primary key,
    descricao              varchar(50)                 not null,
    criadopor              character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem               timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor          character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem           timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao               character varying(8)        NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_medidas
(
    cdmedidas     integer generated always as identity primary key,
    altura        integer                     not null,
    largura       integer                     not null,
    espessura     integer                     not null,
    criadopor     character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem      timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao      character varying(8)        NOT NULL DEFAULT 'ATIVO',
    CONSTRAINT unique_medidas UNIQUE (altura, largura, espessura)
);

CREATE TABLE IF NOT EXISTS public.tb_pai
(
    cdpai                  integer generated always as identity primary key,
    cdcategoria_componente integer references public.tb_categoria_componente (cdcategoria_componente) on update cascade on delete cascade not null,
    cdmodelo               integer references public.tb_modelo (cdmodelo) on update cascade on delete cascade                             not null,
    descricao              varchar(50)                                                                                                    not null,
    bordas_comprimento     integer,
    bordas_largura         integer,
    especial               boolean,
    faces                  integer,
    largura_plastico       integer,
    numero_cantoneiras     integer,
    plastico_acima         boolean,
    plastico_adicional     double precision,
    tipo_pintura           integer,
    tnt_uma_face           boolean,
    criadopor              character varying(50)                                                                                          NOT NULL DEFAULT 'postgres',
    criadoem               timestamp without time zone                                                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor          character varying(50)                                                                                          NOT NULL DEFAULT 'postgres',
    modificadoem           timestamp without time zone                                                                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao               character varying(8)                                                                                           NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_grupo_maquina
(
    cdgrupo_maquina integer generated always as identity primary key,
    nome            varchar(50)                 not null,
    criadopor       character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem        timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor   character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem    timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao        character varying(8)        NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_maquina
(
    cdmaquina       integer generated always as identity primary key,
    cdgrupo_maquina integer references public.tb_grupo_maquina (cdgrupo_maquina) on update cascade on delete cascade not null,
    nome            varchar(50)                                                                                      not null,
    formula         varchar(150)                                                                                     not null,
    valor           double precision,
    criadopor       character varying(50)                                                                            NOT NULL DEFAULT 'postgres',
    criadoem        timestamp without time zone                                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor   character varying(50)                                                                            NOT NULL DEFAULT 'postgres',
    modificadoem    timestamp without time zone                                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao        character varying(8)                                                                             NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_roteiro
(
    cdroteiro     integer generated always as identity primary key,
    descricao     varchar(100),
    implantacao   date,
    data_final    date,
    valor         double precision,
    criadopor     character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem      timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao      character varying(8)        NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_roteiro_maquina
(
    cdroteiro_maquina integer generated always as identity primary key,
    cdroteiro         integer references public.tb_roteiro (cdroteiro) on update cascade on delete cascade not null,
    cdmaquina         integer references public.tb_maquina (cdmaquina) on update cascade on delete cascade not null,
    tempo_homem       double precision,
    tempo_maquina     double precision,
    unidade_medida    varchar(10),
    criadopor         character varying(50)                                                                NOT NULL DEFAULT 'postgres',
    criadoem          timestamp without time zone                                                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor     character varying(50)                                                                NOT NULL DEFAULT 'postgres',
    modificadoem      timestamp without time zone                                                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao          character varying(8)                                                                 NOT NULL DEFAULT 'ATIVO',
    CONSTRAINT unique_roteiro_maquina UNIQUE (cdroteiro, cdmaquina)
);

CREATE TABLE IF NOT EXISTS public.tb_filho
(
    cdfilho        integer generated always as identity primary key,
    cdpai          integer references public.tb_pai (cdpai) on update cascade on delete cascade         not null,
    cdmedidas      integer references public.tb_medidas (cdmedidas) on update cascade on delete cascade not null,
    cdcor          integer references public.tb_cor (cdcor) on update cascade on delete cascade         not null,
    cdroteiro      integer references public.tb_roteiro (cdroteiro) on update cascade on delete cascade,
    descricao      varchar(50)                                                                          not null,
    valor          double precision,
    implantacao    date,
    tipo           integer,
    unidade_medida varchar(10),
    criadopor      character varying(50)                                                                NOT NULL DEFAULT 'postgres',
    criadoem       timestamp without time zone                                                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor  character varying(50)                                                                NOT NULL DEFAULT 'postgres',
    modificadoem   timestamp without time zone                                                          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao       character varying(8)                                                                 NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_item_filho
(
    item_id  integer references public.tb_filho (cdfilho) on update cascade on delete cascade not null,
    filho_id integer references public.tb_filho (cdfilho) on update cascade on delete cascade not null,
    primary key (item_id, filho_id)
);

CREATE TABLE IF NOT EXISTS public.tb_material
(
    cdmaterial        integer generated always as identity primary key,
    dtype             varchar(50)                 not null,
    descricao         varchar(50)                 not null,
    cdcor             integer references public.tb_cor (cdcor) on update cascade on delete cascade,
    implantacao       date,
    porcentagem_perda double precision            not null,
    tipo_material     integer                     not null,
    valor             double precision            not null,
    espessura         integer,
    faces             integer,
    gramatura         double precision,
    altura            integer,
    tipo_pintura      integer,
    criadopor         character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem          timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor     character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem      timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao          character varying(8)        NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_material_usado
(
    cdmaterial_usado   integer generated always as identity primary key,
    dtype              varchar(50)                                                                            not null,
    cdmaterial         integer references public.tb_material (cdmaterial) on update cascade on delete cascade not null,
    cdfilho            integer references public.tb_filho (cdfilho) on update cascade on delete cascade       not null,
    quantidade_bruta   double precision,
    quantidade_liquida double precision,
    valor              double precision,
    unidade_medida     varchar(10),
    criadopor          character varying(50)                                                                  NOT NULL DEFAULT 'postgres',
    criadoem           timestamp without time zone                                                            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor      character varying(50)                                                                  NOT NULL DEFAULT 'postgres',
    modificadoem       timestamp without time zone                                                            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao           character varying(8)                                                                   NOT NULL DEFAULT 'ATIVO',
    CONSTRAINT unique_material_usado UNIQUE (cdmaterial, cdfilho)
);

CREATE TABLE IF NOT EXISTS public.tb_acessorio
(
    cdacessorio   integer generated always as identity primary key,
    descricao     varchar(50)                 not null,
    implantacao   date,
    valor         double precision            not null,
    cdcor         integer references public.tb_cor (cdcor) on update cascade on delete cascade,
    cdmedidas     integer references public.tb_medidas (cdmedidas) on update cascade on delete cascade,
    criadopor     character varying(50)       NOT NULL DEFAULT 'postgres',
    criadoem      timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor character varying(50)       NOT NULL DEFAULT 'postgres',
    modificadoem  timestamp without time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao      character varying(8)        NOT NULL DEFAULT 'ATIVO'
);

CREATE TABLE IF NOT EXISTS public.tb_acessorio_usado
(
    cdacessorio_usado integer generated always as identity primary key,
    cdacessorio       integer references public.tb_acessorio (cdacessorio) on update cascade on delete cascade not null,
    cdfilho           integer references public.tb_filho (cdfilho) on update cascade on delete cascade         not null,
    quantidade        integer                                                                                  not null,
    valor             double precision                                                                         not null,
    unidade_medida    varchar(10),
    criadopor         character varying(50)                                                                    NOT NULL DEFAULT 'postgres',
    criadoem          timestamp without time zone                                                              NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modificadopor     character varying(50)                                                                    NOT NULL DEFAULT 'postgres',
    modificadoem      timestamp without time zone                                                              NOT NULL DEFAULT CURRENT_TIMESTAMP,
    situacao          character varying(8)                                                                     NOT NULL DEFAULT 'ATIVO',
    CONSTRAINT unique_acessorio_usado UNIQUE (cdacessorio, cdfilho)
);