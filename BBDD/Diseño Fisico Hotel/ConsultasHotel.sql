select * from cliente;
select * from reserva;
select * from sala natural join habitacion;
select nom "Hotel", sum(capacidad) "Capacidad" from hotel join sala using(id) natural join habitacion where id = ? group by nom;
select * from sala natural join habitacion;
select * from sala natural join salareuniones;
select * from espacioscomunes;
INSERT INTO Cliente (DNI, nom, ape, tlfno, email, btrabajador, tarifa, pass) VALUES
(?, ?, ?, ?, ?, FALSE, ?, SHA2(?, 256)),