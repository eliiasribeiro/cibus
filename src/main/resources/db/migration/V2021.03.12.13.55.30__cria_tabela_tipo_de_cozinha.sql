create table tipo_de_cozinha (
    id bigint auto_increment,
    nome varchar(50) not null,
    primary key (id)
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;

alter table tipo_de_cozinha add constraint uk_tipo_de_cozinha_nome unique (nome);
