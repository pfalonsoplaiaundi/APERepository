select * from cliente;
select * from reserva;
select * from sala natural join habitacion;
-- select nom "Hotel", sum(capacidad) "Capacidad" from hotel join sala using(id) natural join habitacion where id = ? group by nom;
select * from sala natural join habitacion;
select * from sala natural join salareuniones;
select * from espacioscomunes;
-- INSERT INTO Cliente (DNI, nom, ape, tlfno, email, btrabajador, tarifa, pass) VALUES
-- (?, ?, ?, ?, ?, FALSE, ?, SHA2(?, 256));

SELECT nom FROM hotel;

SELECT h.tipohab, min(r.fecfin)
FROM habitacion h natural join sala s natural join reserva r
where s.id = 1 and r.fecfin > current_date() and r.fecini < current_date()
Group by h.tipohab;

Select *
From habitacion h
	natural join sala s
    natural join reserva r
Where (? not between r.fecini and r.fenfin) and h.TipoHab = ?;

Select s.id, s.num, s.capacidad, s.tlfno, s.pvp, h.tipohab
From habitacion h
	natural join sala s
    natural join reserva r
Where ("2025-01-30" not between r.fecini and r.fecfin) and h.TipoHab = "doble" and h.id = 1;

SELECT h.tipohab, min(case 
	when current_date() between r.fecini and r.fecfin then r.fecfin
    else current_date()
    end),
FROM habitacion h natural join sala s natural join reserva r
where s.id = ? and r.fecfin > current_date()
group by h.tipohab;

SELECT id FROM hotel WHERE nom = "Hotel Central";

SELECT h.tipohab, r.fecfin, r.fecini
FROM habitacion h natural join sala s natural join reserva r
where s.id = 1; /*and r.fecfin > current_date() and r.fecini < current_date()
Group by h.tipohab

select current_date();

*/

SELECT c.dni, c.pass
from cliente c
where c.dni = ? and c.pass = sha2(?, 256);

select * from cliente;
delete from cliente where dni = "04627062Z";

SELECT dni FROM Cliente WHERE DNI = '01234567J';

Select dni, pass, Sha2("Pepe6", 256) from cliente where dni = "04627062Z";