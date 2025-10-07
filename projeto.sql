create database  Planalto_Vistoria;
use Planalto_Vistoria; 

create table Cliente (
Id_Cliente int primary key auto_increment,
Nome varchar (150) not null,
Cpf varchar (11) not null,
Telefone varchar (11) not null,
Email varchar (255) not null
);
create table  Funcionario (
Id_Funcionario int primary key auto_increment,
Nome varchar (150) not null,
Cpf varchar (11) not null,
Cargo varchar (50) not null,
Telefone varchar (11) not null
);
create table Veiculo (
Id_Veiculo int primary key auto_increment,
Placa varchar(8) not null,
Marca varchar (50) not null,
Modelo varchar (80) not null,
Ano varchar (5) not null,
Numero_chassi varchar (17) not null,
Id_Cliente int not null,  -- referencia Id clientes  para definir chave estrangeira
foreign key(Id_Cliente) references Cliente(Id_Cliente)
);
create table Agendamento(
    Id_Agendamento int primary key auto_increment,
    Id_Veiculo int not null,
    Id_Funcionario int not null,
    Id_Cliente int not null,
    Data_Agendamento datetime not null,
    Tipo_Servico varchar(158) not null,
    foreign key (Id_Veiculo) references Veiculo(id_Veiculo),
    foreign key (Id_Funcionario) references Funcionario(id_Funcionario),
    foreign key (Id_Cliente) references Cliente(id_Cliente)
);
create table Vistoria  (
Id_Vistoria int primary key auto_increment,
Id_Funcionario int not null,
Id_Agendamento int not null,
Data_vistoria  datetime not null,
Itens_Verificados text,
Observacao text,
foreign key (Id_Funcionario) references Funcionario(Id_Funcionario),
foreign key (Id_Agendamento) references Agendamento(Id_Agendamento)
);
CREATE TABLE Pagamento (
    Id_Pagamento INT PRIMARY KEY AUTO_INCREMENT,
    Id_Cliente INT NOT NULL,
    Id_Vistoria INT NOT NULL,
    Valor DECIMAL(10,2) NOT NULL,
    Data_Pagamento DATETIME NOT NULL,
    Forma_Pagamento ENUM('Dinheiro', 'Cartao Debito', 'Cartao Credito', 'PIX') NOT NULL,
    Status_Pagamento ENUM('Pendente', 'Pago', 'Cancelado') DEFAULT 'Pendente',
    FOREIGN KEY (Id_Cliente) REFERENCES Cliente(Id_Cliente),
    FOREIGN KEY (Id_Vistoria) REFERENCES Vistoria(Id_Vistoria)
);

CREATE TABLE usuario(
	
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    telefone varchar(11) NOT NULL

);

CREATE TABLE autenticacao(
	idAutenticacao INT PRIMARY KEY AUTO_INCREMENT,
    idFuncionario INT,
    email VARCHAR(255),
    senha VARCHAR(100),
    
	FOREIGN KEY(idFuncionario) REFERENCES Funcionarios(Id_Funcionarios)
);