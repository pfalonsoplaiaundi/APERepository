package conectores;

import java.util.ArrayList;

import model.Cliente;
import model.Reserva;
import model.Sala;

import java.sql.*;

public class RepoReserva {

	// Atributos	
	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	// -----------------------------------------------------------------------------------------------------------------------------------
	
	// Constructor/er
	public RepoReserva() {
		inicializarArray();
	}
	
	// -----------------------------------------------------------------------------------------------------------------------------------
	
	// Metodos propios
	
	/**
	 * Inicializa el repositorio
	 */
	private void inicializarArray() {
		
		// Insertar	0
		this.SQLScripts.add( "INSERT INTO Reserva (DNI, id, num, fecini, fecfin) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)"
				);
		
		// Eliminar 1
		this.SQLScripts.add( "DELETE reserva"
				+ "WHERE codreserva = ?"
				);
		
		// Modificar 2		
		this.SQLScripts.add( "UPDATE cliente "
				+ "SET DNI = ?, nom = ?, ape = ?, tlfno = ?, email = ?, bTrabajador = ?, tarifa = ?, SHA2(pass = ?, 256)"
				+ " WHERE DNI = ?"
				);
		
		// Comprobar existencia	3
		this.SQLScripts.add( "SELECT codreserva "
				+ "FROM reserva "
				+ "WHERE codreserva = ?;"
				);
		
		// Traer informarcion 4	
		this.SQLScripts.add( "SELECT * FROM Reserva "
				+ "WHERE codreserva = ?"
				);
		
		// Otros
		
		// Obtener el ultimo codreserva 5
		this.SQLScripts.add( "SELECT max(codreserva) "
				+ "from reserva;"
				);
		
		this.SQLScripts.add("");
	}

	/**
	 *  Insertar una nueva reserva
	 * @param nuevaReserva
	 * @return
	 */
    public boolean insert(Reserva nuevo) {
    	// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			//Si no existe el cliente, hace la consulta a la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
		        preparedStatement.setString(1, nuevo.getCliente().getDNI());
		        preparedStatement.setInt(2, nuevo.getSala().getHotel().getID());
		        preparedStatement.setInt(3, nuevo.getSala().getNum());
		        preparedStatement.setDate(4, nuevo.getFecIni());
		        preparedStatement.setDate(5, nuevo.getFecFin());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        if (check(nuevo)) {
		        	System.out.print("\n~~~ Reserva creada correctamente ~~~\n");
		        	return true;
		        } else {
		        	System.out.print("\n>>> Se ha producido un error <<<\n\n");
		        	return false;
		        }

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error al insertar la nueva reserva");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		System.out.println("La reserva ya existe");
		return true;
    }

    /**
     * Comprueba que exista o no.
     * @param nuevo
     * @return
     */
    public boolean check(Reserva reserva) {
    	if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(3))) {
	        preparedStatement.setInt(1, reserva.getID());
	        ResultSet rS = preparedStatement.executeQuery();
	        if (rS.next()) {
	        	return true;
	        } else {
	        	return false;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Obtener una reserva por codreserva
	 * @param id
	 * @return
	 */
    public Reserva get(int codreserva) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(this.SQLScripts.get(4))) {
			pS.setInt(1, codreserva);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				RepoHabitacion rH = new RepoHabitacion();
				RepoCliente rC = new RepoCliente();
				Reserva r = new Reserva(
					rS.getInt(1),
					rS.getDate(5),
					rS.getDate(6),
					rC.get(rS.getString(2)),
					rH.get(rS.getInt(3), rS.getInt(4)),
					rS.getDouble(7)
				);
				return r;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
    }

    /**
     * Actualizar una reserva existente
     * @param reservaActualizada
     * @return
     */
    public boolean update(Reserva modificaciones) {

		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		Reserva original = new Reserva(0, null, null, null, null, 0);
		
		// Copruebo que me han pasado el ID correcto
		if (modificaciones.getID() != 0) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getID());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (modificaciones.getFecIni() != null) original.setFecIni(modificaciones.getFecFin());
			if (modificaciones.getFecFin() != null) original.setFecFin(modificaciones.getFecIni());
			if (modificaciones.getCliente() != null) original.setCliente(modificaciones.getCliente());
			if (modificaciones.getSala() != null) original.setSala(modificaciones.getSala());
		
		// En caso de no tener el DNI correcto devuelvo error
		} else {
			System.out.println("Error al insertar el DNI");
			return false;
		}
				
		// Revisa si existe el cliente
		if(check(original)) {
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(2))) {
		        preparedStatement.setDate(1, original.getFecIni());
		        preparedStatement.setDate(2, original.getFecFin());
		        preparedStatement.setString(3, original.getCliente().getDNI());
		        preparedStatement.setInt(4, original.getSala().getHotel().getID());
		        preparedStatement.setInt(4, original.getSala().getNum());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
		        return !check(original);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al actualizar el cliente");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
    }

	
	public int getNewID() {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareCall(SQLScripts.get(5))) {
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				return rS.getInt(1)+1;
			} else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public ArrayList<Reserva> getListaFiltrada(Reserva filtro) {
		String query = 
				"SELECT  "
					+ "R.FECINI, "
					+ "R.FECFIN, "
					+ "R.DNI, "
					+ "H.NOM, "
					+ "S.NUM, "
					+ "R.CODRESERVA "
				+ "FROM "
					+ "RESERVA R "
					+ "JOIN CLIENTE C USING(DNI) "
					+ "JOIN SALA S USING(ID, NUM) "
					+ "JOIN HOTEL H USING(ID) "
				+ "WHERE "
					+ "(h.nom = ? or ? = \"\") and "
					+ "(c.dni = ? or ? = \"\") and "
					+ "(r.num = ? or ? = 0) and "
					+ "(r.fecini >= ? or ? is null) and "
					+ "(r.fecini <= ? or ? is null) "
				+ "ORDER BY "
					+ "R.FECINI ASC, "
					+ "R.FECFIN ASC;";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			if (filtro.getSala() != null) {
				if (filtro.getSala().getHotel() != null) {
					pS.setString(1, filtro.getSala().getHotel().getNombre());
					pS.setString(2, filtro.getSala().getHotel().getNombre());
				} else {
					pS.setString(1, "");
					pS.setString(2, "");
				}
				pS.setInt(5, filtro.getSala().getNum());
				pS.setInt(6, filtro.getSala().getNum());
			} else {
				pS.setString(1, "");
				pS.setString(2, "");
				pS.setInt(5, 0);
				pS.setInt(6, 0);
			}
			
			if (filtro.getCliente() != null) {
				pS.setString(3, filtro.getCliente().getDNI());
				pS.setString(4, filtro.getCliente().getDNI());
			} else {
				pS.setString(3, "");
				pS.setString(4, "");
			}
				
			if (filtro.getFecIni() != null) {
				pS.setDate(7, filtro.getFecIni());
				pS.setDate(8, filtro.getFecIni());
			} else {
				pS.setDate(7, null);
				pS.setDate(8, null);
			}
			
			if (filtro.getFecFin() != null) {
				pS.setDate(9, filtro.getFecFin());
				pS.setDate(10, filtro.getFecFin());
			} else {
				pS.setDate(9, null);
				pS.setDate(10, null);
			}
			ResultSet rS = pS.executeQuery();
						
			ArrayList<Reserva> lista = new ArrayList<>();
			RepoCliente rC = new RepoCliente();
			RepoHotel rH = new RepoHotel();
			while (rS.next()) {
				Sala s = new Sala(
								rS.getInt(5), 
								0, 
								"", 
								0, 
								rH.get(rH.getPKByName(rS.getString(4))),
								""
							);
				Reserva r = new Reserva(
								rS.getInt(6),
								rS.getDate(1),
								rS.getDate(2),
								rC.get(rS.getString(3)),
								s,
								0
							);
				lista.add(r);
			}
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}