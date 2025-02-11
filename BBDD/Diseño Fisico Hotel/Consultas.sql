-- Sala de reuniones
DELETE FROM 
	sala
WHERE 
	id = ? and
    num = ?;

SELECT 
	* 
FROM 
	salareuniones
WHERE 
	id = ? and 
    num = ?;

SELECT 
	* 
FROM 
	salareuniones 
    natural join sala
WHERE 
	id = ? and 
    num = ?;

SELECT 
	h.id, 
	s.num, 
	s.capacidad, 
	s.pvp, 
	s.tlfno, 
	r.servicios 
FROM 
	Salareuniones r 
	JOIN sala s USING(id, num) 
	JOIN HOTEL H USING(ID) 
WHERE 
	(s.num = ? or ? = 0) and 
	(s.id = ? or ? = 0) 
ORDER BY 
	h.id ASC, 
	s.num ASC;
    
INSERT INTO 
	salaReuniones (id, num, servicios) 
VALUES 
	(?, ?, ?);
    
-- Sala
SELECT 
	* 
FROM 
	sala 
WHERE 
	id = ? and 
	num = ?;
    
SELECT 
	h.tipohab, 
	MIN(s.pvp)
FROM 
	habitacion h
	NATURAL JOIN sala s
	LEFT JOIN reserva r USING(id, num)
WHERE 
	h.id = ? AND 
    (
		r.id IS NULL OR 
        (
			r.fecfin > CURRENT_DATE() AND 
            r.fecIni NOT BETWEEN ? AND ? AND 
            r.fecFin NOT BETWEEN ? AND ?
		)
	) AND 
    h.num not in (/* Interrogantes variables, que se rellenan de las reservas flotantes */"") 
GROUP BY 
	h.tipohab;
    
INSERT sala (id, num, capacidad, tlfno, pvp, subtipo) VALUES (?, ?, ?, ?, ?, ?);
    
SELECT * FROM sala WHERE id = ? and num = ?;
           
UPDATE sala SET capacidad = ?, tlfno = ?, pvp = ? WHERE id = ? and num = ?;

INSERT INTO Reserva (DNI, id, num, fecini, fecfin, bPagada) VALUES (?, ?, ?, ?, ?, ?);

SELECT codreserva FROM reserva WHERE codreserva = ?;

SELECT * FROM Reserva WHERE codreserva = ?;

SELECT max(codreserva) from reserva;

UPDATE reserva SET fecini = ?, fecfin = ?, dni = ?, id = ?, num = ? WHERE codreserva = ?;

SELECT 
	R.FECINI, 
    R.FECFIN, 
    R.DNI, 
    H.NOM, 
	S.NUM, 
    R.CODRESERVA 
FROM 
	RESERVA R 
    JOIN CLIENTE C USING(DNI) 
    JOIN SALA S USING(ID, NUM) 
    JOIN HOTEL H USING(ID) 
WHERE 
	(h.nom = ? or ? = "") and 
	(c.dni = ? or ? = "") and 
    (r.num = ? or ? = 0) and 
    (r.fecini >= ? or ? is null) and 
    (r.fecini <= ? or ? is null) 
ORDER BY 
	R.FECINI ASC, 
    R.FECFIN ASC;

DELETE FROM 
	reserva 
WHERE 
	codreserva = ?;
    
-- Hotel
INSERT Hotel (nom, ciu, dir, tlfno, email) VALUES (?, ?, ?, ?, ?); 

DELETE FROM hotel WHERE id = ?;

UPDATE hotel SET id = ?, nom = ?, ciu = ?, dir = ?, tlfno = ?, email = ? WHERE id = ?;

SELECT * FROM hotel WHERE id = ?;

SELECT nom FROM hotel;

SELECT id FROM hotel WHERE nom like ?;

SELECT * FROM hotel WHERE id = ?;

SELECT * FROM hotel WHERE id = ?;

SELECT 
	h.nom, 
	h.ciu, 
	h.dir, 
	h.tlfno, 
	h.email, 
	h.id 
FROM 
	Hotel h 
WHERE 
	(h.nom = ? or ? = "") and 
	(h.tlfno = ? or ? = "") and 
	(h.email = ? or ? = "") and 
	(h.ciu = ? or ? = "") 
ORDER BY 
	h.nom ASC, 
    h.id ASC;
    
-- Habitacion
INSERT INTO habitacion (id, num, tipohab) VALUES (?, ?, ?);

DELETE FROM sala WHERE id = ? and num = ?;

UPDATE habitacion SET tipoHab = ? WHERE id = ? and num = ?;

SELECT * FROM habitacion WHERE id = ? and num = ?;

SELECT * FROM habitacion natural join sala WHERE id = ? and num = ?;

SELECT 
	s.num, 
    s.capacidad, 
    s.tlfno, 
    s.pvp, 
    h.tipohab 
FROM 
	habitacion h 
    JOIN sala s USING(id, num) 
    LEFT JOIN reserva r ON 
		r.id = s.id AND 
        r.num = s.num AND 
        ? BETWEEN r.FecIni AND r.FecFin AND 
        ? BETWEEN r.fecini and r.fecfin 
WHERE 
	r.id IS null AND 
    h.tipohab = ? AND 
    h.id = ? AND 
    h.num not in (/*Dinamico de interrogantes segun reservas flotantes*/"") 
ORDER BY 
	s.pvp ASC;

SELECT 
	h.nom, 
    s.num, 
    s.capacidad, 
    s.pvp, 
    s.tlfno, 
    ha.tipohab, 
    case 
		when current_date() between r.fecini and r.fecfin then true 
        else false 
	end
FROM 
	habitacion ha 
    JOIN sala s USING(id, num) 
    JOIN HOTEL H USING(ID) 
    LEFT JOIN reserva r USING(id, num) 
WHERE 
	(h.nom = ? or ? = "") and 
    (h.ciu = ? or ? = "") 
ORDER BY 
	h.id ASC, 
	s.num ASC;

-- Espacios comunes
SELECT 
	h.id, 
    s.num, 
	s.capacidad, 
    s.pvp, 
    s.tlfno, 
    e.tipo 
FROM 
	EspaciosComunes e 
    JOIN sala s USING(id, num) 
    JOIN HOTEL H USING(ID) 
WHERE 
	(h.nom = ? or ? = "") and 
	(h.ciu = ? or ? = "") 
ORDER BY 
	h.id ASC, 
    s.num ASC;
    
INSERT INTO espaciosComunes (id, num, tipo) VALUES (?, ?, ?);

SELECT * FROM espacioscomunes WHERE id = ? and num = ?;

UPDATE espacioscomunes SET tipo = ? WHERE id = ? and num = ?;

SELECT * FROM espacioscomunes natural join sala WHERE id = ? and num = ?;

DELETE FROM sala WHERE id = ? and num = ?;

-- Cliente
INSERT INTO Cliente (DNI, nom, ape, tlfno, email, btrabajador, tarifa, pass) VALUES (?, ?, ?, ?, ?, ?, ?, SHA2(?, 256));

DELETE FROM cliente WHERE DNI = ?;

UPDATE cliente SET DNI = ?, nom = ?, ape = ?, tlfno = ?, email = ?, bTrabajador = ?, tarifa = ?, pass = SHA2( ?, 256) WHERE DNI = ?;

SELECT dni FROM Cliente WHERE DNI = ?;

SELECT * FROM Cliente WHERE DNI = ?;

SELECT c.dni, c.pass from cliente c where c.dni = ? and c.pass = sha2(?, 256);

SELECT 
	c.nom, 
    c.ape, 
    c.DNI, 
    c.tlfno, 
    c.email, 
    c.btrabajador, 
    c.tarifa 
FROM 
	cliente c 
WHERE 
	(c.nom = ? or ? = "") and 
	(c.tlfno = ? or ? = "") and 
	(c.email = ? or ? = 0) 
ORDER BY 
	c.nom ASC, 
	c.ape ASC;
    
SELECT 
	c.nom, 
    c.ape, 
    c.DNI, 
    c.tlfno, 
    c.email, 
    c.btrabajador, 
    c.tarifa 
FROM 
	cliente c 
ORDER BY 
	c.nom ASC, 
    c.ape ASC;
