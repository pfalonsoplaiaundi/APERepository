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
    end)
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

select * from cliente;

select * from hotel;

SELECT h.tipohab, min(case 
	when current_date() between r.fecini and r.fecfin then r.fecfin
    else current_date()
    end)
FROM habitacion h natural join sala s natural join reserva r
where s.id = ? and r.fecfin > current_date()
group by h.tipohab;


select s.id, s.num, s.capacidad, s.tlfno, s.pvp, h.tipohab
from habitacion h
	natural join sala s
    left join reserva r on 
		r.id = s.id and
        r.num = s.num and
        current_date() between r.FecIni and r.FecFin
where
	r.id is null and
    h.tipohab = ? and
    h.id = ?;
    
    select * from habitacion natural join sala where id = 1 and num = 1;
    
SELECT
	h.nom,
    ha.num,
    ha.tipoHab,
    s.tlfno,
    s.pvp,
    case
		when current_date() between r.fecini and r.fecfin then true
        else false
    end "Ocupada"
FROM habitacion ha
	JOIN sala s using (id, num)
    join hotel h using(id)
    left jOIN reserva r using( id, num)
WHERE
    (h.nom = "" or h.nom <> "") and
    (ha.tipoHab = "" or ha.TipoHab <> "") and
	( = "" or h.nom <> "") and
	(h.nom = "" or h.nom <> "") and
	(h.nom = "" or h.nom <> "");
    
    
    select * FROM habitacion ha
	JOIN sala s using (id, num)
    jOIN reserva r using( id, num)
    join hotel h using(id)
    ;
    
SELECT  R.FECINI, R.FECFIN, R.DNI, C.NOM, C.APE, H.NOM, S.NUM
FROM RESERVA R JOIN CLIENTE C USING(DNI) JOIN SALA S USING(ID, NUM) JOIN HOTEL H USING(ID)
WHERE
    (h.nom = "Hotel Central" or "Hotel Central" = "") and
    (c.dni = "" or "" = "") and
	(r.num = 0 or 0 = 0) and
	(r.fecini = null or null is null) and
	(r.fecfin = null or null is null)
ORDER BY R.FECINI ASC, R.FECFIN ASC;

select * from sala;