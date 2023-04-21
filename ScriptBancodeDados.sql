-- Criação do banco de dados da aplicação
create database dbfinfac;
-- Selecionado o banco de dados
use dbfinfac;
-- Criação da tabela de usuarios
create table tbusuarios(
	iduser int primary key auto_increment, 
    nome varchar(50) not null,
    email VARCHAR(50) NOT NULL unique,
	datanascimento DATE,
	idade INT,
	parentesco VARCHAR(50),
    cpf VARCHAR(11) unique,
    fone varchar(15) unique,
    login varchar(15) not null unique,
    senha varchar(15) not null
);
select * from tbusuarios;
alter table tbusuarios add column perfil varchar(20) not null;

update tbusuarios set perfil='admin' where iduser =2;
update tbusuarios set perfil='admin' where iduser =1;

ALTER TABLE tbusuarios DROP idade;
ALTER TABLE tbusuarios ADD photo longblob;

describe tbusuarios;

insert into tbusuarios(nome, email,login,senha) values ("João Vitor Fernandes de Sales","joaosales911@gmail.com","joaosales","joaosales1234");
insert into tbusuarios(nome, email, login, senha) values ("Administrador","admin@gmail.com","admin","admin");
insert into tbusuarios(nome, email, login, senha, perfil) values ("Pessoa 01","pessoa01@gmail.com","pessoa01","pessoa01","user");
select * from tbusuarios;

delete from tbusuarios where login = "joaosales";

update tbusuarios set fone="98891-9268" where login = "joaosales";

update tbusuarios set datanascimento = '2003-04-14' where login = 'joaosales';

CREATE TABLE tbeventos (
  id INT AUTO_INCREMENT primary key,
  descricao VARCHAR(100) NOT NULL,
  data DATE NOT NULL,
  horario TIME NOT NULL,
  local VARCHAR(100) NOT NULL,
  validado BOOLEAN
);
alter table tbeventos add  categoria varchar(50);
ALTER TABLE tbeventos CHANGE id idevento INT;
ALTER TABLE tbeventos MODIFY idevento int auto_increment;

alter table tbeventos change local localevento varchar(100);
alter table tbeventos change data dataevento date;
describe tbeventos;


INSERT INTO tbeventos (descricao, data, horario, local, validado)  VALUES ('Passear com o cachorro', '2023-04-15', '15:30:00', 'Bosque da UFERSA', true);
insert into tbeventos (descricao, dataevento, horario, localevento, categoria, validado) Values ('Ida ao shopping com a cremosa','2023-04-14','17:44:00','Partagem Shopping Mossoró','Passeio',true);
SET SQL_SAFE_UPDATES = 1;

UPDATE tbeventos SET descricao = 'realizar as atividades referentes a disciplina de APOO', localevento = 'minha casa' WHERE dataevento = '2023-04-15';

select * from tbeventos;

create table tbgastosevento(
	idgasto int primary key auto_increment,
    datacomputador timestamp default current_timestamp,
    datagasto date,
    horagasto time,
    categoria varchar(50) not null,
    descricao varchar(255),
    valor decimal(10,2) not null,
    meiopagamento varchar(50) not null,
    observacoes text,
    idevento int not null,
    foreign key(idevento) references tbeventos(idevento)
);

describe tbgastosevento;
select * from tbgastosevento;
insert into tbgastosevento (categoria, descricao, valor, meiopagamento, observacoes, idevento)
Values ('Transporte','Pedindo um uber para me encontrar com a cremosa',32.32,'PIX','Vou ter que ir de moto na proxima vez',2);
describe tbeventos;
select Gastos.valor,meiopagamento,
		Evento.descricao,dataevento,localevento
from tbgastosevento as Gastos inner join tbeventos as Evento on (Gastos.idevento = Evento.idevento);