create table marca (
	id serial not null,
	descricao varchar (100) not null,
	constraint pk_marca primary key (id),
	constraint uq_marca unique (descricao)
);

create table modelo (
	id serial not null,
	descricao varchar (100) not null,
	id_marca bigint not null,
	constraint pk_modelo primary key (id),
    constraint fk_modelo_marca foreign key (id_marca) references marca (id),
	constraint uq_modelo unique (descricao)
);

create table automovel (
	id serial not null,
	placa varchar (10) not null,
	numero_portas int not null,
	tipo_combustivel varchar (20) not null,
	cor varchar (30) not null,
	ano integer not null,
	chassi varchar (50) not null,
	valor_diaria numeric (8, 2) not null,
    id_modelo bigint not null,
	constraint pk_automovel primary key (id),
    constraint fk_automovel_modelo foreign key (id_modelo) references modelo (id),
	constraint uq_automovel unique (chassi)
);

create table cliente (
	id serial not null,
	cpf varchar (15) not null,
	nome varchar (100) not null,
	data_nascimento date not null,
	telefone varchar (15) not null,
	email varchar (50) not null,
	endereco_logradouro varchar (100) not null,
	endereco_numero integer not null,
	endereco_complemento varchar (100),
	endereco_bairro varchar (100) not null,
	endereco_cep varchar (10) not null,
	endereco_cidade varchar (100) not null,
	endereco_uf varchar (2) not null,
	constraint pk_cliente primary key (id),
	constraint uq_cliente unique (cpf)
);

create table locacao (
	id serial not null,
	data_locacao date not null,
	data_devolucao date not null,
	valor numeric (8, 2)not null,
    id_automovel bigint not null,
    id_cliente bigint not null,
	constraint pk_locacao primary key (id),
    constraint fk_locacao_automovel foreign key (id_automovel) references automovel (id),
    constraint fk_locacao_cliente foreign key (id_cliente) references cliente (id)
);