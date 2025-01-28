Use Hotel; 

DROP TABLE IF EXISTS EspaciosComunes;
DROP TABLE IF EXISTS SalaReuniones;
DROP TABLE IF EXISTS Reserva;
DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Habitacion;
DROP TABLE IF EXISTS Sala;
DROP TABLE IF EXISTS Hotel;

-- Tabla de Cliente
CREATE TABLE IF NOT EXISTS Cliente (
  DNI Char(9) Primary Key,
  nom varchar(50) NOT NULL,
  ape varchar(50) NOT NULL,
  tlfno int NOT NULL constraint ck_tlf_cliente check(tlfno between 100000000 and 999999999),
  email varchar(80) NOT NULL,
  btrabajador BOOLEAN NOT NULL,  
  tarifa enum ("estandar",
		"dctoTrabajador",
		"dcto5",
		"dcto10",
		"dcto5por",
		"dcto10por",
		"dctoNewCliente"
        ) DEFAULT "estandar",
  pass varchar(64) NOT NULL
);

-- Tabla de Hotel
CREATE TABLE  IF NOT EXISTS Hotel (
  ID tinyint unsigned AUTO_INCREMENT PRIMARY KEY,
  nom varchar(50),
  dir varchar(80) NOT NULL,
  tlfno int NOT NULL constraint ck_tlf_hotel check(tlfno between 100000000 and 999999999),
  email varchar(80) NOT NULL
);

-- Tabla de Sala
CREATE TABLE IF NOT EXISTS Sala (
  ID tinyint unsigned,
  num smallint unsigned,
  capacidad smallint unsigned,
  tlfno int NOT NULL constraint ck_tlf_sala check(tlfno between 100000000 and 999999999),
  pvp decimal,
  subtipo enum("Habitacion", "SalaReuniones", "EspaciosComunes") NOT NULL,
  constraint PK_Sala primary key (id, num)
);

-- Tabla de SalaReuniones
CREATE TABLE IF NOT EXISTS SalaReuniones (
  ID tinyint unsigned,
  num smallint unsigned,
  servicios varchar(100) not null,
  constraint PK_SalaReuniones primary key (id, num)
);

-- Tabla de Habitacion
CREATE TABLE IF NOT EXISTS Habitacion (
  ID tinyint unsigned,
  num smallint unsigned,
  TipoHab enum("individual","doble","familiar","suite","apartamento") not null,
  constraint PK_Habitacion primary key (id, num)
);

-- Tabla de EspaciosComunes
CREATE TABLE EspaciosComunes (
  ID tinyint unsigned,
  num smallint unsigned,
  Tipo varchar(50),
  constraint PK_EspaciosComunes primary key (id, num)
);

-- Tabla de Reserva
CREATE TABLE IF NOT EXISTS Reserva (
  CodReserva int unsigned AUTO_INCREMENT PRIMARY KEY,
  DNI Char(9) not null,
  ID tinyint unsigned not null,
  num smallint unsigned not null,
  FecIni date NOT NULL,
  FecFin date NOT NULL
);

-- Relaciones foreign keys
ALTER TABLE EspaciosComunes 
	ADD CONSTRAINT EspaciosComunes_IDnum_fk FOREIGN KEY (ID, num) REFERENCES Sala(ID, num) ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE Habitacion 
	ADD CONSTRAINT Habitacion_IDnum_fk FOREIGN KEY (ID, num) REFERENCES Sala(ID, num) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE SalaReuniones 
	ADD CONSTRAINT SalaReuniones_IDnum_fk FOREIGN KEY (ID, num) REFERENCES Sala(ID, num) ON UPDATE CASCADE ON DELETE CASCADE; 

ALTER TABLE Reserva
	ADD CONSTRAINT Reserva_IDnum_fk FOREIGN KEY (ID, num) REFERENCES Sala(ID, num) ON UPDATE CASCADE ON DELETE CASCADE,
   	ADD CONSTRAINT Reserva_DNI_fk FOREIGN KEY (DNI) REFERENCES Cliente(DNI) ON UPDATE CASCADE ON DELETE CASCADE;
    
ALTER TABLE Sala 
	ADD CONSTRAINT Sala_ID_fk FOREIGN KEY (ID) REFERENCES Hotel(ID) ON UPDATE CASCADE ON DELETE CASCADE;


