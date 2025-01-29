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

SELECT h.tipohab, min(case 
	when current_date() between r.fecini and r.fecfin then r.fecfin
    else current_date()
    end) "Fecha mas cercana"
FROM habitacion h natural join sala s natural join reserva r
where s.id = 1 and r.fecfin > current_date()
group by h.tipohab;
;

WHERE current_date() between r.fecini and r.fecfin


SELECT h.tipohab, r.fecfin, r.fecini
FROM habitacion h natural join sala s natural join reserva r
where s.id = 1; /*and r.fecfin > current_date() and r.fecini < current_date()
Group by h.tipohab

select current_date();

*/
