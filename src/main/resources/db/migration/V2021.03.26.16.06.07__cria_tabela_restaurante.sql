create table restaurante (
    id bigint auto_increment,
    nome varchar(50) not null,
    endereco varchar(200) not null,
    cnpj varchar(20) not null,
    cep varchar(10) not null,
    descricao text,
    tipo_de_cozinha_id bigint not null,
    primary key (id)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

alter table restaurante add constraint fk_restaurante_tipo_de_cozinha foreign key (tipo_de_cozinha_id) references tipo_de_cozinha(id);
