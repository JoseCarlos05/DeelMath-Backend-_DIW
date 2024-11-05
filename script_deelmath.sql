CREATE TABLE public.usuario (
	id serial NOT NULL,
	contrasena varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	fecha_nacimiento date NOT NULL,
	nombre varchar(255) NOT NULL,
	telefono varchar(255) NOT NULL,
	CONSTRAINT usuario_pkey PRIMARY KEY (id)
);


CREATE TABLE grupo (
	id serial NOT NULL,
	moneda int2 NULL,
	nombre varchar(255) NOT NULL,
	CONSTRAINT grupo_moneda_check CHECK (((moneda >= 0) AND (moneda <= 8))),
	CONSTRAINT grupo_pkey PRIMARY KEY (id)
);


CREATE TABLE gasto (
	id serial NOT NULL,
	categoria int2 NULL,
	coste float8 NOT NULL,
	fecha date NOT NULL,
	titulo varchar(255) NOT NULL,
	id_grupo int4 NULL,
	id_usuario int4 NULL,
	CONSTRAINT gasto_categoria_check CHECK (((categoria >= 0) AND (categoria <= 4))),
	CONSTRAINT gasto_pkey PRIMARY KEY (id)
);
ALTER TABLE gasto ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id);
ALTER TABLE gasto ADD CONSTRAINT fk_grupo FOREIGN KEY (id_grupo) REFERENCES grupo(id);


CREATE TABLE usuario_grupo (
	id_grupo int4 NOT NULL,
	id_usuario int4 NOT NULL,
	CONSTRAINT usuario_grupo_pkey PRIMARY KEY (id_grupo, id_usuario)
);
ALTER TABLE usuario_grupo ADD CONSTRAINT fk_usuario1 FOREIGN KEY (id_usuario) REFERENCES public.usuario(id);
ALTER TABLE usuario_grupo ADD CONSTRAINT fk_grupo1 FOREIGN KEY (id_grupo) REFERENCES public.grupo(id);


CREATE TABLE amistad (
	id serial NOT NULL,
	amigo_id int4 NULL,
	usuario_id int4 NULL,
	CONSTRAINT amistad_pkey PRIMARY KEY (id)
);
ALTER TABLE amistad ADD CONSTRAINT fk_usuarioa FOREIGN KEY (usuario_id) REFERENCES usuario(id);
ALTER TABLE amistad ADD CONSTRAINT fk_amigo FOREIGN KEY (amigo_id) REFERENCES usuario(id);


CREATE TABLE balance (
	id serial NOT NULL,
	balance float8 NOT NULL,
	grupo_id int4 NULL,
	usuario_id int4 NULL,
	CONSTRAINT balance_pkey PRIMARY KEY (id)
);
ALTER TABLE balance ADD CONSTRAINT fk_grupob FOREIGN KEY (grupo_id) REFERENCES grupo(id);
ALTER TABLE balance ADD CONSTRAINT fk_usuariob FOREIGN KEY (usuario_id) REFERENCES usuario(id);
