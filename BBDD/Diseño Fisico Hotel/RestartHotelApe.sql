CREATE DATABASE IF NOT EXISTS Hotel;

USE Hotel; 

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
  tlfno varchar(15) NOT NULL,
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
  nom varchar(50) NOT NULL unique,
  ciu varchar(50) NOT NULL,
  dir varchar(80) NOT NULL,
  tlfno varchar(15) NOT NULL,
  email varchar(80) NOT NULL
);

-- Tabla de Sala
CREATE TABLE IF NOT EXISTS Sala (
  ID tinyint unsigned,
  num smallint unsigned,
  capacidad smallint unsigned constraint CK_capacidad_sala check (capacidad > 0),
  tlfno varchar(15) NOT NULL,
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
  FecFin date NOT NULL,
  PrecioTotal decimal NOT NULL DEFAULT 0,
  bPagada boolean NOT NULL DEFAULT false
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
   	ADD CONSTRAINT Reserva_DNI_fk FOREIGN KEY (DNI) REFERENCES Cliente(DNI) ON UPDATE CASCADE ON DELETE CASCADE,
    ADD CONSTRAINT Reserva_Fec CHECK (fecini <= fecfin);
    
ALTER TABLE Sala 
	ADD CONSTRAINT Sala_ID_fk FOREIGN KEY (ID) REFERENCES Hotel(ID) ON UPDATE CASCADE ON DELETE CASCADE;

-- Tabla Hotel
INSERT INTO Hotel VALUES
(1, 'Hotel Central', 'Madrid', 'Calle Mayor, 1', 911234567, 'central@hotel.com'),
(2, 'Hotel Sur', 'Sevilla', 'Avenida del Sol, 22', 950123456, 'sur@hotel.com'),
(3, 'Hotel Norte', 'Donostia', 'Calle Fría, 10', 945678912, 'norte@hotel.com'),
(4, 'Hotel Este', 'Lleida', 'Paseo del Este, 45', 934567891, 'este@hotel.com'),
(5, 'Hotel Oeste', 'Salamanca', 'Avenida del Oeste, 78', 923456789, 'oeste@hotel.com');

-- Tabla Sala
INSERT INTO Sala (ID, num, capacidad, tlfno, pvp, subtipo) VALUES
-- Salas para Hotel Central (ID = 1)
(1, 1, 2, 911111111, 80.00, 'Habitacion'),
(1, 2, 2, 911111112, 85.00, 'Habitacion'),
(1, 3, 4, 911111113, 120.00, 'Habitacion'),
(1, 4, 50, 911111114, 300.00, 'SalaReuniones'),
(1, 5, 20, 911111115, 150.00, 'EspaciosComunes'),
(1, 6, 2, 911111111, 80.00, 'Habitacion'),
(1, 7, 2, 911111112, 85.00, 'Habitacion'),
(1, 8, 4, 911111113, 120.00, 'Habitacion'),
(1, 9, 1, 911111114, 300.00,  'Habitacion'),
(1, 10, 1, 911111115, 150.00,  'Habitacion'),
(1, 11, 2, 911111111, 80.00, 'Habitacion'),
(1, 12, 2, 911111112, 85.00, 'Habitacion'),
(1, 13, 4, 911111113, 120.00, 'Habitacion'),
(1, 14, 3, 911111114, 300.00,  'Habitacion'),
(1, 15, 3, 911111115, 150.00,  'Habitacion'),


-- Salas para Hotel Sur (ID = 2)
(2, 1, 1, 950111111, 70.00, 'Habitacion'),
(2, 2, 2, 950111112, 90.00, 'Habitacion'),
(2, 3, 4, 950111113, 110.00, 'Habitacion'),
(2, 4, 60, 950111114, 350.00, 'SalaReuniones'),
(2, 5, 25, 950111115, 180.00, 'EspaciosComunes'),

-- Salas para Hotel Norte (ID = 3)
(3, 1, 2, 945111111, 75.00, 'Habitacion'),
(3, 2, 2, 945111112, 80.00, 'Habitacion'),
(3, 3, 3, 945111113, 100.00, 'Habitacion'),
(3, 4, 40, 945111114, 250.00, 'SalaReuniones'),
(3, 5, 30, 945111115, 160.00, 'EspaciosComunes'),

-- Salas para Hotel Este (ID = 4)
(4, 1, 1, 934111111, 65.00, 'Habitacion'),
(4, 2, 3, 934111112, 95.00, 'Habitacion'),
(4, 3, 4, 934111113, 105.00, 'Habitacion'),
(4, 4, 70, 934111114, 400.00, 'SalaReuniones'),
(4, 5, 15, 934111115, 140.00, 'EspaciosComunes'),

-- Salas para Hotel Oeste (ID = 5)
(5, 1, 2, 923111111, 85.00, 'Habitacion'),
(5, 2, 2, 923111112, 95.00, 'Habitacion'),
(5, 3, 3, 923111113, 115.00, 'Habitacion'),
(5, 4, 55, 923111114, 310.00, 'SalaReuniones'),
(5, 5, 18, 923111115, 170.00, 'EspaciosComunes');

-- Tabla Habitacion
INSERT INTO Habitacion (ID, num, TipoHab) VALUES
(1, 1, 'doble'),
(1, 2, 'doble'),
(1, 3, 'apartamento'),
(1, 6, 'doble'),
(1, 7, 'suite'),
(1, 8, 'apartamento'),
(1, 9, 'individual'),
(1, 10, 'individual'),
(1, 11, 'apartamento'),
(1, 12, 'doble'),
(1, 13, 'familiar'),
(1, 14, 'familiar'),
(1, 15, 'familiar'),
(2, 1, 'individual'),
(2, 2, 'doble'),
(2, 3, 'familiar'),
(3, 1, 'doble'),
(3, 2, 'doble'),
(3, 3, 'suite'),
(4, 1, 'individual'),
(4, 2, 'familiar'),
(4, 3, 'apartamento'),
(5, 1, 'doble'),
(5, 2, 'doble'),
(5, 3, 'familiar');

-- Tabla EspaciosComunes
INSERT INTO EspaciosComunes (ID, num, Tipo) VALUES
(1, 5, 'Piscina'),
(2, 5, 'Gimnasio'),
(3, 5, 'Spa'),
(4, 5, 'Salón de Juegos'),
(5, 5, 'Terraza');

-- Tabla SalaReuniones
INSERT INTO SalaReuniones (ID, num, servicios) VALUES
(1, 4, 'Proyector, Pizarra'),
(2, 4, 'Wifi, Teleconferencia'),
(3, 4, 'Cafetera, Proyector'),
(4, 4, 'Micrófonos, Altavoces'),
(5, 4, 'TV, Mesa redonda');

-- Tabla Clientes
INSERT INTO Cliente (DNI, nom, ape, tlfno, email, btrabajador, tarifa, pass) VALUES
('12345678A', 'Laura', 'García', 611223344, 'laura.garcia@mail.com', FALSE, 'estandar', SHA2('Contraseña1', 256)),
('23456789D', 'Carlos', 'Martínez', 622334455, 'carlos.martinez@mail.com', TRUE, 'dctoTrabajador', SHA2('Contraseña1', 256)),
('34567890C', 'Ana', 'López', 633445566, 'ana.lopez@mail.com', FALSE, 'dcto5', SHA2('Contraseña1', 256)),
('45678901D', 'David', 'Hernández', 644556677, 'david.hernandez@mail.com', FALSE, 'dcto10', SHA2('Contraseña1', 256)),
('56789012E', 'Marta', 'Pérez', 655667788, 'marta.perez@mail.com', TRUE, 'dcto5por', SHA2('Contraseña1', 256)),
('67890123F', 'Jorge', 'Gómez', 666778899, 'jorge.gomez@mail.com', FALSE, 'dcto10por', SHA2('Contraseña1', 256)),
('78901234G', 'Sara', 'Ruiz', 677889900, 'sara.ruiz@mail.com', FALSE, 'dctoNewCliente', SHA2('Contraseña1', 256)),
('89012345H', 'Luis', 'Díaz', 688990011, 'luis.diaz@mail.com', TRUE, 'estandar', SHA2('Contraseña1', 256)),
('90123456I', 'Elena', 'Moreno', 699001122, 'elena.moreno@mail.com', FALSE, 'dcto5', SHA2('Contraseña1', 256)),
('01234567J', 'Pedro', 'Vega', 611112223, 'pedro.vega@mail.com', TRUE, 'dcto10', SHA2('Contraseña1', 256)),
('04627062Z', 'Pablo', 'Fernandez', 626140550, 'pfalonso@gmail.com', TRUE, 'dctoTrabajador', SHA2('@6966olbaP', 256));

-- Tabla Reserva
INSERT INTO Reserva (DNI, ID, num, fecini, fecfin) VALUES
-- Reservas para llenar el Hotel Central del 28-01-2025 al 20-02-2025
('12345678A', 1, 1, '2025-02-28', '2025-03-20'),
('23456789D', 1, 2, '2025-01-28', '2025-02-20'),
('34567890C', 1, 3, '2025-02-02', '2025-02-20'),
('45678901D', 1, 4, '2025-01-28', '2025-02-20'),
('56789012E', 1, 5, '2025-01-13', '2025-01-29'),
('12345678A', 1, 6, '2025-02-28', '2025-03-20'),
('23456789D', 1, 7, '2025-01-28', '2025-02-02'),
('34567890C', 1, 8, '2025-04-20', '2025-05-20'),
('45678901D', 1, 9, '2025-01-28', '2025-02-20'),
('56789012E', 1, 10, '2025-01-28', '2025-02-20'),
('12345678A', 1, 11, '2025-02-28', '2025-03-20'),
('23456789D', 1, 12, '2025-01-28', '2025-02-20'),
('34567890C', 1, 13, '2025-01-28', '2025-02-20'),
('45678901D', 1, 14, '2025-01-28', '2025-02-20'),
('56789012E', 1, 15, '2025-01-28', '2025-02-20'),
('04627062Z', 1, 1, '2025-02-05', '2025-02-15'),


-- Reservas para otros hoteles en las mismas fechas y adicionales
('67890123F', 2, 1, '2025-02-01', '2025-02-05'),
('78901234G', 2, 2, '2025-02-03', '2025-02-10'),
('89012345H', 3, 3, '2025-01-29', '2025-02-12'),
('90123456I', 4, 4, '2025-01-30', '2025-02-15'),
('01234567J', 5, 5, '2025-02-02', '2025-02-18');

-- Usuarios
CREATE USER IF NOT EXISTS app@127.0.0.1 IDENTIFIED BY "app";
CREATE USER IF NOT EXISTS appAdm@127.0.0.1 IDENTIFIED BY "appAdm";
GRANT INSERT, SELECT ON hotel.* TO app@127.0.0.1;
GRANT UPDATE, DELETE, INSERT, SELECT ON hotel.* TO appAdm@127.0.0.1;
