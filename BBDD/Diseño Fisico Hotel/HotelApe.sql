Use Hotel; 

DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Sala;
DROP TABLE IF EXISTS Hotel;
DROP TABLE IF EXISTS SalaReuniones;
DROP TABLE IF EXISTS Habitacion;
DROP TABLE IF EXISTS EspaciosComunes;
DROP TABLE IF EXISTS Reserva;

-- Tabla de Cliente
CREATE TABLE IF NOT EXISTS Cliente (
  DNI int AUTO_INCREMENT PRIMARY KEY,
  nom varchar(30) NOT NULL,
  ape varchar(30) NOT NULL,
  tlfno char(9) NOT NULL UNIQUE,
  email varchar(80) NOT NULL UNIQUE,
  btrabajador BOOLEAN NOT NULL,  
  tarifa decimal DEFAULT 0.00,
  pass varchar(80) NOT NULL
);

-- Tabla de Hotel
CREATE TABLE  IF NOT EXISTS Hotel (
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  nom varchar(30),
  dir varchar(80) NOT NULL,
  tlfno char(9) NOT NULL,
  email varchar(80) UNIQUE NOT NULL
);

-- Tabla de Sala
CREATE TABLE IF NOT EXISTS Sala (
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  num int UNIQUE,
  capacidad int,
  tlfno char(9),
  pvp decimal,
  subtipo varchar(50) NOT NULL
);

-- Tabla de SalaReuniones
CREATE TABLE IF NOT EXISTS SalaReuniones (
  ID int AUTO_INCREMENT PRIMARY KEY,
  num int UNIQUE,
  servicios varchar(50)
);

-- Tabla de Habitacion
CREATE TABLE IF NOT EXISTS Habitacion (
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  num int UNIQUE,
  TipoHab varchar(80) UNIQUE
);

-- Tabla de EspaciosComunes
CREATE TABLE EspaciosComunes (
  ID int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  num int UNIQUE,
  Tipo varchar(50)
);

-- Tabla de Reserva
CREATE TABLE IF NOT EXISTS Reserva (
  CodReserva int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  DNI int NOT NULL,
  ID int NOT NULL,
  Num int NOT NULL,
  FecIni date NOT NULL,
  FecFin date NOT NULL,
  FOREIGN KEY (DNI) REFERENCES Cliente(DNI),
  FOREIGN KEY (ID) REFERENCES Hotel(ID),
  FOREIGN KEY (Num) REFERENCES Sala(num)
);

-- Relaciones foreign keys
ALTER TABLE EspaciosComunes ADD CONSTRAINT EspaciosComunes_ID_fk FOREIGN KEY (ID) REFERENCES Reserva(CodReserva);
ALTER TABLE EspaciosComunes ADD CONSTRAINT EspaciosComunes_num_fk FOREIGN KEY (num) REFERENCES Sala(num);
ALTER TABLE Habitacion ADD CONSTRAINT Habitacion_ID_fk FOREIGN KEY (ID) REFERENCES Hotel(ID);
ALTER TABLE Habitacion ADD CONSTRAINT Habitacion_num_fk FOREIGN KEY (num) REFERENCES Sala(num);
ALTER TABLE SalaReuniones ADD CONSTRAINT SalaReuniones_ID_fk FOREIGN KEY (ID) REFERENCES Reserva(CodReserva);
ALTER TABLE SalaReuniones ADD CONSTRAINT SalaReuniones_num_fk FOREIGN KEY (num) REFERENCES Sala(num); 





