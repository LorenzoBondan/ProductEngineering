
    create table public.trash (
       id bigint generated always as identity,
       table_name varchar(50) not null,
        date timestamp not null,
        entity_id jsonb not null,
        username varchar(50) not null,
        status varchar(255) not null,
        referenced_table varchar(200) not null,
        CONSTRAINT pk_trash PRIMARY KEY (id),
        CONSTRAINT uk_trash UNIQUE (entity_id)
    );

    create table tb_color (
       code bigint not null,
       name varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_color_pk primary key (code),
        CONSTRAINT tb_color_uk UNIQUE (name)
    );

   create table tb_guide (
       id bigint generated always as identity,
       final_date date,
        implementation date,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_guide_pk primary key (id)
    );

      create table tb_item (
       dtype varchar(31) not null,
        code bigint not null,
        description varchar(255) not null,
        measure1 integer,
        measure2 integer,
        measure3 integer,
        measurement_unit varchar(255),
        faces integer,
        high_shine boolean,
        satin boolean,
        satin_glass boolean,
        special boolean,
        suffix integer,
        color_id bigint,
        father_id bigint,
        guide_id bigint,
        aluminium_type_id bigint,
        used_drawer_pull_id bigint,
        used_glass_id bigint,
        back_id bigint,
        ghost_id varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_item_pk PRIMARY KEY (code)
    );

    create table tb_aluminium_son_son (
       aluminium_son_id bigint not null,
        son_id bigint not null,
        CONSTRAINT tb_aluminium_son_son_pk PRIMARY KEY (aluminium_son_id, son_id)
    );

    create table tb_aluminium_type (
       id bigint generated always as identity,
       name varchar(255),
       less_quantity double precision,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_aluminium_type_pk primary key (id),
        CONSTRAINT tb_aluminium_type_uk UNIQUE (name)
    );

    create table tb_back (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        measure1 integer,
        measure2 integer,
        suffix integer,
        thickness double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_back_pk primary key (code),
        CONSTRAINT tb_back_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_back_uk UNIQUE (description)
    );

    create table tb_corner_bracket (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_corner_bracket_pk primary key (code),
        CONSTRAINT tb_corner_bracket_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_corner_bracket_uk UNIQUE (description)
    );

    create table tb_edge_banding (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        height integer,
        thickness integer,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_edge_banding_pk primary key (code),
        CONSTRAINT tb_edge_banding_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_edge_banding_uk UNIQUE (description)
    );

    create table tb_father_attachment (
       father_id bigint not null,
        attachment_id bigint not null,
        CONSTRAINT tb_father_attachment_pk primary key (father_id, attachment_id),
        CONSTRAINT tb_father_attachment_fk_father FOREIGN KEY (father_id) REFERENCES tb_item (code),
        CONSTRAINT tb_father_attachment_fk_attachment FOREIGN KEY (attachment_id) REFERENCES tb_item (code)
    );

    create table tb_ghost (
       code varchar(255) not null,
       description varchar(255) not null,
        measure1 integer,
        measure2 integer,
        measure3 integer,
        suffix varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_ghost_pk primary key (code),
        CONSTRAINT tb_ghost_uk UNIQUE (description)
    );

    create table tb_glue (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        grammage double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_glue_pk primary key (code),
        CONSTRAINT tb_glue_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_glue_uk UNIQUE (description)
    );

   create table tb_machine_group (
       id bigint generated always as identity,
       name varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_machine_group_pk primary key (id),
        CONSTRAINT tb_machine_group_uk UNIQUE (name)
    );

   create table tb_machine (
       id bigint generated always as identity,
       name varchar(255),
       formula character varying(255)[],
        machine_group_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_machine_pk primary key (id),
        CONSTRAINT tb_machine_fk_machine_group FOREIGN KEY (machine_group_id) REFERENCES tb_machine_group (id),
        CONSTRAINT tb_machine_uk UNIQUE (name)
    );

    create table tb_guide_machine (
       id bigint generated always as identity,
       guide_id bigint not null,
        machine_id bigint not null,
        machine_time double precision,
        man_time double precision,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_guide_machine_pk primary key (id),
        CONSTRAINT tb_guide_machine_fk_guide FOREIGN KEY (guide_id) REFERENCES tb_guide (id),
        CONSTRAINT tb_guide_machine_fk_machine FOREIGN KEY (machine_id) REFERENCES tb_machine (id),
        CONSTRAINT tb_guide_machine_uk UNIQUE (guide_id, machine_id)
    );

    create table tb_material (
       id bigint generated always as identity,
       name varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_material_pk primary key (id),
        CONSTRAINT tb_material_uk UNIQUE (name)
    );

    create table tb_nonwoven_fabric (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_nonwoven_fabric_pk primary key (code),
        CONSTRAINT tb_nonwoven_fabric_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_nonwoven_fabric_uk UNIQUE (description)
    );

   create table tb_painting_type (
       id bigint generated always as identity,
        description varchar(255) not null,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_painting_type_pk primary key (id),
        CONSTRAINT tb_painting_type_uk UNIQUE (description)
    );

    create table tb_painting (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        color_id bigint,
        painting_type_id bigint not null,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_painting_pk primary key (code),
        CONSTRAINT tb_painting_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_painting_fk_painting_type FOREIGN KEY (painting_type_id) REFERENCES tb_painting_type (id),
        CONSTRAINT tb_painting_uk UNIQUE (description, painting_type_id)
    );

    create table tb_painting_border_background (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_painting_border_background_pk primary key (code),
        CONSTRAINT tb_painting_border_background_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_painting_border_background_uk UNIQUE (description)
    );

    create table tb_plastic (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        grammage double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_plastic_pk primary key (code),
        CONSTRAINT tb_plastic_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_plastic_uk UNIQUE (description)
    );

    create table tb_polyester (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_polyester_pk primary key (code),
        CONSTRAINT tb_polyester_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_polyester_uk UNIQUE (description)
    );

    create table tb_polyethylene (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        color_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_polyethylene_pk primary key (code),
        CONSTRAINT tb_polyethylene_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_polyethylene_uk UNIQUE (description)
    );

    create table tb_role (
       id bigint generated always as identity,
        authority varchar(255),
        CONSTRAINT tb_role_pk primary key (id),
        CONSTRAINT tb_role_uk UNIQUE (authority)
    );

    create table tb_sheet (
       code bigint not null,
       description varchar(255) not null,
        family varchar(255),
        implementation date,
        lost_percentage double precision,
        faces integer,
        thickness double precision,
        color_id bigint,
        material_id bigint,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_sheet_pk primary key (code),
        CONSTRAINT tb_sheet_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
        CONSTRAINT tb_sheet_fk_material FOREIGN KEY (material_id) REFERENCES tb_material (id)
    );

    create table tb_used_back_sheet (
       id bigint generated always as identity,
       back_id bigint not null,
       sheet_id bigint not null,
       net_quantity double precision,
       gross_quantity double precision,
       measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_back_sheet_pk primary key (id),
        CONSTRAINT tb_used_back_sheet_fk_back FOREIGN KEY (back_id) REFERENCES tb_back (code),
        CONSTRAINT tb_used_back_sheet_fk_sheet FOREIGN KEY (sheet_id) REFERENCES tb_sheet (code),
        CONSTRAINT tb_used_back_sheet_uk UNIQUE (back_id, sheet_id)
    );

    create table tb_used_corner_bracket (
       id bigint generated always as identity,
      corner_bracket_id bigint not null,
      ghost_id varchar(255) not null,
      net_quantity double precision,
      gross_quantity double precision,
      measurement_unit varchar(255),
      quantity integer,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
         CONSTRAINT tb_used_corner_bracket_pk primary key (id),
        CONSTRAINT tb_used_corner_bracket_fk_ghost FOREIGN KEY (ghost_id) REFERENCES tb_ghost (code),
        CONSTRAINT tb_used_corner_bracket_fk_corner_bracket FOREIGN KEY (corner_bracket_id) REFERENCES tb_corner_bracket (code),
        CONSTRAINT tb_used_corner_bracket_uk UNIQUE (ghost_id, corner_bracket_id)
    );

    create table tb_used_drawer_pull (
       id bigint generated always as identity,
      drawer_pull_id bigint not null,
      aluminium_son_id bigint not null,    
        quantity double precision,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_drawer_pull_pk primary key (id),
        CONSTRAINT tb_used_drawer_pull_fk_aluminium_son FOREIGN KEY (aluminium_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_drawer_pull_fk_drawer_pull FOREIGN KEY (drawer_pull_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_drawer_pull_uk UNIQUE (aluminium_son_id, drawer_pull_id)
    );

    create table tb_used_edge_banding (
       id bigint generated always as identity,
      edge_banding_id bigint not null,
        son_id bigint not null,
        net_quantity double precision,
        gross_quantity double precision,
        edge_length integer,
        edge_width integer,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_edge_banding_pk primary key (id),
        CONSTRAINT tb_used_edge_banding_fk_edge_banding FOREIGN KEY (edge_banding_id) REFERENCES tb_edge_banding (code),
        CONSTRAINT tb_used_edge_banding_fk_son FOREIGN KEY (son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_edge_banding_uk UNIQUE (edge_banding_id, son_id)
    );

    create table tb_used_glass (
       id bigint generated always as identity,
       glass_id bigint not null,
       aluminium_son_id bigint not null,
       quantity double precision,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_glass_id primary key (id),
        CONSTRAINT tb_used_glass_fk_glass FOREIGN KEY (glass_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_glass_fk_aluminium_son FOREIGN KEY (aluminium_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_glass_uk UNIQUE (glass_id, aluminium_son_id)
    );

    create table tb_used_glue (
       id bigint generated always as identity,
         glue_id bigint not null,
         son_id bigint not null,
         net_quantity double precision,
         gross_quantity double precision,
         measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_glue_pk primary key (id),
        CONSTRAINT tb_used_glue_fk_glue FOREIGN KEY (glue_id) REFERENCES tb_glue (code),
        CONSTRAINT tb_used_glue_fk_son FOREIGN KEY (son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_glue_uk UNIQUE (glue_id, son_id)
    );

    create table tb_used_molding (
       id bigint generated always as identity,
       molding_id bigint not null,
       aluminium_son_id bigint not null,
       quantity double precision,
       measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_molding_pk primary key (id),
        CONSTRAINT tb_used_molding_fk_molding FOREIGN KEY (molding_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_molding_fk_aluminium_son FOREIGN KEY (aluminium_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_molding_uk UNIQUE (molding_id, aluminium_son_id)
    );

    create table tb_used_nonwoven_fabric (
       id bigint generated always as identity,
       nonwoven_fabric_id bigint not null,
        ghost_id varchar(255) not null,
        net_quantity double precision,
        gross_quantity double precision,
        one_face boolean,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_nonwoven_fabric_pk primary key (id),
        CONSTRAINT tb_used_nonwoven_fabric_fk_nonwoven_fabric FOREIGN KEY (nonwoven_fabric_id) REFERENCES tb_nonwoven_fabric (code),
        CONSTRAINT tb_used_nonwoven_fabric_fk_ghost FOREIGN KEY (ghost_id) REFERENCES tb_ghost (code),
        CONSTRAINT tb_used_nonwoven_fabric_uk UNIQUE (nonwoven_fabric_id, ghost_id)
    );

    create table tb_used_painting (
       id bigint generated always as identity,
      painting_id bigint not null,
      painting_son_id bigint not null,
      net_quantity double precision,
      gross_quantity double precision,
      measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_painting_pk primary key (id),
        CONSTRAINT tb_used_painting_fk_painting FOREIGN KEY (painting_id) REFERENCES tb_painting (code),
        CONSTRAINT tb_used_painting_fk_painting_son FOREIGN KEY (painting_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_painting_uk UNIQUE (painting_id, painting_son_id)
    );

    create table tb_used_painting_border_background (
       id bigint generated always as identity,
       painting_border_background_id bigint not null,
       painting_son_id bigint not null,
       net_quantity double precision,
       gross_quantity double precision,
       measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_painting_border_background_pk primary key (id),
        CONSTRAINT tb_used_painting_border_background_fk_pb_background FOREIGN KEY (painting_border_background_id) REFERENCES tb_painting_border_background (code),
        CONSTRAINT tb_used_painting_border_background_fk_painting_son FOREIGN KEY (painting_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_painting_border_background_uk UNIQUE (painting_border_background_id, painting_son_id)
    );

    create table tb_used_plastic (
       id bigint generated always as identity,
         plastic_id bigint not null,
         ghost_id varchar(255) not null,
         net_quantity double precision,
         gross_quantity double precision,
        additional double precision,
        upper boolean,
        width integer,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_plastic_pk primary key (id),
        CONSTRAINT tb_used_plastic_fk_plastic FOREIGN KEY (plastic_id) REFERENCES tb_plastic (code),
        CONSTRAINT tb_used_plastic_fk_ghost FOREIGN KEY (ghost_id) REFERENCES tb_ghost (code),
        CONSTRAINT tb_used_plastic_uk UNIQUE (plastic_id, ghost_id)
    );

    create table tb_used_polyester (
       id bigint generated always as identity,
         polyester_id bigint not null,
         painting_son_id bigint not null,
         net_quantity double precision,
         gross_quantity double precision,
         measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_polyester_pk primary key (id),
        CONSTRAINT tb_used_polyester_fk_polyester FOREIGN KEY (polyester_id) REFERENCES tb_polyester (code),
        CONSTRAINT tb_used_polyester_fk_painting_son FOREIGN KEY (painting_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_polyester_uk UNIQUE (polyester_id, painting_son_id)
    );

    create table tb_used_polyethylene (
       id bigint generated always as identity,
         polyethylene_id bigint not null,
         ghost_id varchar(255) not null,
         net_quantity double precision,
         gross_quantity double precision,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_polyethylene_pk primary key (id),
        CONSTRAINT tb_used_polyethylene_fk_polyethylene FOREIGN KEY (polyethylene_id) REFERENCES tb_polyethylene (code),
        CONSTRAINT tb_used_polyethylene_fk_ghost FOREIGN KEY (ghost_id) REFERENCES tb_ghost (code),
        CONSTRAINT tb_used_polyethylene_uk UNIQUE (polyethylene_id, ghost_id)
    );

    create table tb_used_screw (
       id bigint generated always as identity,
       screw_id bigint not null,
       aluminium_son_id bigint not null,
       quantity double precision,
       measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_screw_pk primary key (id),
        CONSTRAINT tb_used_screw_fk_screw FOREIGN KEY (screw_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_screw_fk_aluminium_son FOREIGN KEY (aluminium_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_screw_uk UNIQUE (screw_id, aluminium_son_id)
    );

    create table tb_used_sheet (
       id bigint generated always as identity,
         sheet_id bigint not null,
        son_id bigint not null,
        net_quantity double precision,
        gross_quantity double precision,
        measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_sheet_pk primary key (id),
        CONSTRAINT tb_used_sheet_fk_sheet FOREIGN KEY (sheet_id) REFERENCES tb_sheet (code),
        CONSTRAINT tb_used_sheet_fk_son FOREIGN KEY (son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_sheet_uk UNIQUE (sheet_id, son_id)
    );

    create table tb_used_try_square (
       id bigint generated always as identity,
       try_square_id bigint not null,
       aluminium_son_id bigint not null,
       quantity double precision,
       measurement_unit varchar(255),
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_used_try_square_pk primary key (id),
        CONSTRAINT tb_used_try_square_fk_try_square FOREIGN KEY (try_square_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_try_square_fk_aluminium_son FOREIGN KEY (aluminium_son_id) REFERENCES tb_item (code),
        CONSTRAINT tb_used_try_square_uk UNIQUE (try_square_id, aluminium_son_id)
    );

    create table tb_user (
       id bigint generated always as identity,
       name varchar(255),
       email varchar(255),
       password varchar(255),
       img_url TEXT,
        created_by varchar(50),
        creation_date timestamp,
        last_modified_by varchar(50),
        last_modified_date timestamp,
        status varchar(255),
        CONSTRAINT tb_user_pk primary key (id),
        CONSTRAINT tb_user_uk UNIQUE (email)
    );

    create table tb_user_role (
       user_id bigint not null,
        role_id bigint not null,
        CONSTRAINT tb_user_role_pk primary key (user_id, role_id),
        CONSTRAINT tb_user_role_fk_user FOREIGN KEY (user_id) REFERENCES tb_user (id),
        CONSTRAINT tb_user_role_fk_role FOREIGN KEY (role_id) REFERENCES tb_role (id)
    );

ALTER TABLE tb_item 
   ADD CONSTRAINT tb_item_fk_color FOREIGN KEY (color_id) REFERENCES tb_color (code),
   ADD CONSTRAINT tb_item_fk_father FOREIGN KEY (father_id) REFERENCES tb_item (code),
   ADD CONSTRAINT tb_item_fk_guide FOREIGN KEY (guide_id) REFERENCES tb_guide (id),
   ADD CONSTRAINT tb_item_fk_aluminium_type FOREIGN KEY (aluminium_type_id) REFERENCES tb_aluminium_type (id),
   ADD CONSTRAINT tb_item_fk_used_drawer_pull FOREIGN KEY (used_drawer_pull_id) REFERENCES tb_used_drawer_pull (id),
   ADD CONSTRAINT tb_item_fk_used_glass FOREIGN KEY (used_glass_id) REFERENCES tb_used_glass (id),
   ADD CONSTRAINT tb_item_fk_back FOREIGN KEY (back_id) REFERENCES tb_back (code),
   ADD CONSTRAINT tb_item_fk_ghost FOREIGN KEY (ghost_id) REFERENCES tb_ghost (code);

ALTER TABLE tb_aluminium_son_son 
   ADD CONSTRAINT tb_aluminium_son_son_fk_mdp_son FOREIGN KEY (son_id) REFERENCES tb_item (code),
   ADD CONSTRAINT tb_aluminium_son_son_fk_aluminium_son FOREIGN KEY (aluminium_son_id) REFERENCES tb_item (code),
   ADD CONSTRAINT tb_aluminium_son_son_uk UNIQUE (aluminium_son_id, son_id);
	   
INSERT INTO tb_user (name, email, password, img_Url, status) VALUES ('Alex', 'alex@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/47.jpg', 'ACTIVE');
INSERT INTO tb_user (name, email, password, img_Url, status) VALUES ('Maria', 'maria@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://i.pinimg.com/originals/76/ef/b7/76efb7c94755748d695d3d46cf11d08d.jpg', 'ACTIVE');
INSERT INTO tb_user (name, email, password, img_Url, status) VALUES ('Bob', 'bob@gmail.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', 'https://xsgames.co/randomusers/assets/avatars/male/62.jpg', 'ACTIVE');
INSERT INTO tb_role (authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (3, 1);
INSERT INTO tb_color (code, name, status) VALUES (307, 'MINERALE', 'ACTIVE');
INSERT INTO tb_color (code, name, status) VALUES (302, 'PRISMA', 'ACTIVE');
INSERT INTO tb_color (code, name, status) VALUES (308, 'STONE', 'ACTIVE');
INSERT INTO tb_material (name, status) VALUES ('MDP', 'ACTIVE');
INSERT INTO tb_material (name, status) VALUES ('MDF', 'ACTIVE');
INSERT INTO tb_sheet (code, description, thickness, implementation, faces, lost_percentage, color_id, material_id, status) VALUES (1022602, 'MDP BP 18MM - MINERALE/CZ COBALTO 2F', 18.0, null, 2, 13, 307, 1, 'ACTIVE');
INSERT INTO tb_sheet (code, description, thickness, implementation, faces, lost_percentage, color_id, material_id, status) VALUES (1022610, 'MDP BP 18MM - PRISMA 2F', 18.0, null, 2, 13, 302, 1, 'ACTIVE');
INSERT INTO tb_sheet (code, description, thickness, implementation, faces, lost_percentage, color_id, material_id, status) VALUES (1022618, 'MDP BP 18MM - STONE/CHUMBO 2F', 18.0, null, 2, 13, 308, 1, 'ACTIVE');
INSERT INTO tb_sheet (code, description, thickness, implementation, faces, lost_percentage, color_id, material_id, status) VALUES (10, 'MDF BP 18 MM 2F', 18, null, 2, 10 , null, 2, 'ACTIVE');
INSERT INTO tb_sheet (code, description, thickness, implementation, faces, lost_percentage, color_id, material_id, status) VALUES (11, 'MDF BP 18 MM 1F', 18, null, 1, 10 , null, 2, 'ACTIVE');
INSERT INTO tb_edge_banding (code, description, height, thickness, family, implementation, lost_percentage, color_id, status) VALUES (1022604, 'FITA BORDA PS 2107 - MINERALE/CZ COBALTO', 21, 7, '11FITABO', null, 8.6, 307, 'ACTIVE');
INSERT INTO tb_edge_banding (code, description, height, thickness, family, implementation, lost_percentage, color_id, status) VALUES (1022613, 'FITA BORDA PS 2107 - PRISMA', 21, 7, '11FITABO', null, 8.6, 302, 'ACTIVE');
INSERT INTO tb_edge_banding (code, description, height, thickness, family, implementation, lost_percentage, color_id, status) VALUES (1022620, 'FITA BORDA PS 2107 - STONE/CHUMBO', 21, 7, '11FITABO', null, 8.6, 308, 'ACTIVE');
INSERT INTO tb_glue (code, description, grammage, lost_percentage, status) VALUES (1025359,	'COLA PUR CQ 645', 0.09, 12, 'ACTIVE');
INSERT INTO tb_corner_bracket (code, description, status) VALUES (111111, 'CORNER BRACKET 1', 'ACTIVE');
INSERT INTO tb_nonwoven_fabric (code, description, lost_percentage, status) VALUES (222222, 'NONWOVEN FABRIC 1', 10.0, 'ACTIVE');
INSERT INTO tb_plastic (code, description, grammage, lost_percentage, status) VALUES (333333, 'PLASTIC 1', 0.5, 10, 'ACTIVE');
INSERT INTO tb_polyethylene (code, description, lost_percentage, status) VALUES (444444, 'POLYETHYLENE 1', 10, 'ACTIVE');
INSERT INTO tb_painting_type (description, status) VALUES ('Satin', 'ACTIVE');
INSERT INTO tb_painting_type (description, status) VALUES ('High Shine', 'ACTIVE');
INSERT INTO tb_painting_type (description, status) VALUES ('Satin Glass', 'ACTIVE');
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, status) VALUES (201, 'MINERALE', 10, 1, 'ACTIVE');
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, status) VALUES (202, 'PRISMA', 10, 1, 'ACTIVE');
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, status) VALUES (203, 'STONE', 10, 1, 'ACTIVE');
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, status) VALUES (204, 'MINERALE', 10, 2, 'ACTIVE');
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, status) VALUES (205, 'PRISMA', 10, 2, 'ACTIVE');
INSERT INTO tb_painting (code, description, lost_percentage, painting_type_id, status) VALUES (206, 'STONE', 10, 2, 'ACTIVE');
INSERT INTO tb_painting_border_background (code, description, lost_percentage, status) VALUES (30, 'Standard Painting Border', 10, 'ACTIVE');
INSERT INTO tb_polyester (code, description, lost_percentage, status) VALUES (40, 'Standard Polyester', 10, 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status) VALUES (1019262, 'ACAB FTE GAV ALUM TRANSLUCIDO 810 MM', 810, 16, 16, 'Attachment', 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status) VALUES (70002299, 'ACESSÓRIO GAVETA ALUM H-90 P-500', 500, 90, 18, 'Attachment', 'ACTIVE');
INSERT INTO tb_aluminium_type (name, less_quantity, status) VALUES ('MOCCA', 0.5, 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status) VALUES (888, 'PUXADOR ALUMINIO', 192, 35, 5, 'DrawerPull', 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, color_id, dtype, status) VALUES (10108000, 'VIDRO TEMPERADO MINERALE', 2205, 581, 18, 307, 'Glass', 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, color_id, dtype, status) VALUES (10108001, 'VIDRO TEMPERADO PRISMA', 2205, 581, 18, 302, 'Glass', 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status) VALUES (155687, 'PERFIL BAGUETE 123', null, null, null, 'Molding', 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status) VALUES (158963, 'PARAFUSO 18 MM BRANCO', null, null, null, 'Screw', 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status) VALUES (158964, 'PARAFUSO 18 MM PRETO', null, null, null, 'Screw', 'ACTIVE');
INSERT INTO tb_item (code, description, measure1, measure2, measure3, dtype, status) VALUES (1693258, 'TRY SQUARE 3456', null, null, null, 'TrySquare', 'ACTIVE');
INSERT INTO tb_machine_group (name, status) VALUES ('CNCs', 'ACTIVE');