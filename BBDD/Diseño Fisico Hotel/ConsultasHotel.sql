select * from cliente;
select * from reserva;
select * from sala natural join habitacion;
select nom "Hotel", sum(capacidad) "Capacidad" from hotel join sala using(id) natural join habitacion group by nom;
select * from sala natural join habitacion;
select * from sala natural join salareuniones;
select * from espacioscomunes;