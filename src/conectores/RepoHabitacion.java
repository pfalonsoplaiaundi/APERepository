package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import menu.MenuPrincipal;
import model.Cliente;
import model.HabDisponible;
import model.Habitacion;
import model.Sala;

public class RepoHabitacion {
	
	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	public RepoHabitacion() {
		inicializarArray();
	}
	
	private void inicializarArray() {
		
		// Insertar	0
		this.SQLScripts.add( "INSERT INTO habitacion (id, num, tipohab) "
				+ "VALUES "
				+ "(?, ?, ?)"
				);
		
		// Eliminar 1
		this.SQLScripts.add( "DELETE "
				+ "WHERE id = ? and num = ?"
				);
		
		// Modificar 2		
		this.SQLScripts.add( "UPDATE habitacion "
				+ "SET id = ?, num = ?, tipoHab = ? "
				+ " WHERE id = ? and num = ?"
				);
		
		// Comprobar existencia	3
		this.SQLScripts.add( "SELECT * FROM habitacion "
				+ " WHERE id = ? and num = ?;"
				);
		
		// Traer informarcion 4	
		this.SQLScripts.add( "SELECT * FROM habitacion natural join sala "
				+ " WHERE id = ? and num = ?"
				);
		
		// Otros
		// Recuperar la habitacion libre mas cercana de un tipo 6
		this.SQLScripts.add("select s.num, s.capacidad, s.tlfno, s.pvp, h.tipohab\r\n"
				+ "from habitacion h\r\n"
				+ "	natural join sala s\r\n"
				+ "    left join reserva r on \r\n"
				+ "		r.id = s.id and\r\n"
				+ "        r.num = s.num and\r\n"
				+ "        current_date() between r.FecIni and r.FecFin\r\n"
				+ "where\r\n"
				+ "	r.id is null and\r\n"
				+ "    h.tipohab = ? and\r\n"
				+ "    h.id = ?;");
	}
	
	/**
	 * Esta funcion inserta un cliente nuevo en la tabla cliente con todos los parametros de cliente. Devuelve true siempre que el usuario exista o haya sido insertado.
	 */
	public boolean insert(Habitacion nuevo) {
			
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			//Si no existe el cliente, hace la consulta a la BBDD
	        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
	            preparedStatement.setInt(1, nuevo.getNum());
	            preparedStatement.setInt(2, nuevo.getCapacidad());
	            preparedStatement.setString(3, nuevo.getTlfno());
	            preparedStatement.setString(4, nuevo.getTipo().toString());
	            preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        if (check(nuevo)) {
		        	System.out.print("\n~~~ Habitacion creada correctamente ~~~\n");
		        	return true;
		        } else {
		        	System.out.print("\n>>> Se ha producido un error <<<\n\n");
		        	return false;
		        }

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error al insertar la nueva habitacion");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		System.out.println("El usuario ya existe");
		return true;
	}

	/**
	 * Esta funcion borra un cliente nuevo en la tabla cliente con todos los parametros de cliente
	 */
	public boolean delete(Habitacion aBorrar) {
				
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si existe el cliente
		if(check(aBorrar)) {
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(1))) {
	            preparedStatement.setInt(1, aBorrar.getNum());
	            preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve lo contrario en funcion de esta
		        return !check(aBorrar);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al eliminar la habitacion");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	/**
	 * Esta funcion modifica un cliente nuevo en la tabla cliente con todos los parametros de cliente
	 */
	public boolean update(Habitacion modificaciones) {
	/*
	 * WIP
	 */
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		Habitacion original = new Habitacion(null, 0, 0, "", 0, "");
		
		// Copruebo que me han pasado el Primary Key correcto
		if (!((modificaciones.getNum() != 0) && (modificaciones.getHotel() == null))) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getHotel().getID(), modificaciones.getNum());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (modificaciones.getCapacidad() != 0) original.setCapacidad(modificaciones.getCapacidad());
			if (!modificaciones.getTlfno().equals("")) original.setTlfno(modificaciones.getTlfno());
			if (modificaciones.getPvp() != 0) original.setPvp(modificaciones.getPvp());
			if (!modificaciones.getTipo().equals(Habitacion.tipoHab.desconocido)) original.setTipo(modificaciones.getTipo());
		
		// En caso de no tener el DNI correcto devuelvo error
		} else {
			System.out.println("Error al insertar el DNI");
			return false;
		}
				
		// Revisa si existe el cliente
		if(check(original)) {
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(2))) {
		        preparedStatement.setInt(1, original.getHotel().getID());
		        preparedStatement.setInt(2, original.getNum());
		        preparedStatement.setInt(3, original.getCapacidad());
		        preparedStatement.setString(4, original.getTlfno());
		        preparedStatement.setDouble(5, original.getPvp());
		        preparedStatement.setString(6, original.getTipo().toString());
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
	

	public boolean check(Habitacion habitacion) {
		
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(3))) {
	        preparedStatement.setInt(1, habitacion.getHotel().getID());
			preparedStatement.setInt(2, habitacion.getNum());
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
	
	public boolean checkEquals(Cliente cliente) {
		/*
		 * WIP
		 */
		
		return false;
	}

	public Habitacion get(int idHotel, int num) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(this.SQLScripts.get(4))) {
			pS.setInt(1, idHotel);
			pS.setInt(2, num);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				RepoHotel rH = new RepoHotel();
				Habitacion h = new Habitacion(
					rH.get(rS.getInt(1)),
					rS.getInt(2),
					rS.getInt(4),
					rS.getString(5),
					rS.getDouble(6),
					rS.getString(3)
				);
				return h;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	

	public Habitacion getByTypeAndFirstDate(String tipoDeHab) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(this.SQLScripts.get(6))) {
			pS.setString(1, tipoDeHab);
			pS.setInt(2, MenuPrincipal.hotel.getID());
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				Habitacion h = new Habitacion(
					MenuPrincipal.hotel,
					rS.getInt(1),
					rS.getInt(2),
					rS.getString(3),
					rS.getDouble(4),
					rS.getString(5)	
				);
				return h;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<HabDisponible> getListaFiltrada(Habitacion filtro, int disponible) {
		ArrayList<HabDisponible> lista = new ArrayList<>();
		
		return null;
	}


}
