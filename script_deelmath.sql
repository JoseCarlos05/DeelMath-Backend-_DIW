create table usuarios(
	id serial primary key,
	nombre varchar(40) unique not null,
	email varchar(35) unique not null,
	telefono char(9) not null,
	contrasena varchar(30) unique not null,
	fecha_nacimiento date not null
);

create table grupos(
	id serial primary key,
	nombre varchar(40) unique not null,
	moneda int not null
);

create table gastos(
	id serial primary key,
	titulo varchar(25) not null,
	coste float8 not null,
	fecha date not null,
	categoria int not null,
	id_usuario int not null,
	id_grupo int not null,
	constraint fk_usuario 
	foreign key (id_usuario) references usuarios (id),
	constraint fk_grupo 
	foreign key (id_grupo) references grupos (id)
);


create table usuario_grupo(
	id_usuario int not null,
	id_grupo int not null,
	constraint fk_usuario 
	foreign key (id_usuario) references usuarios (id),
	constraint fk_grupo 
	foreign key (id_grupo) references grupos (id)
)