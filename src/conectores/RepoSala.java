package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import menu.MenuCarrito;
import menu.MenuPrincipal;
import menu.MenuProductos;
import model.EspacioComun;
import model.Habitacion;
import model.Habitacion.tipoHab;
import model.Sala.tSala;
import model.Reserva;
import model.Sala;
import model.SalaReunion;

/**
 * Repositorio de consultas de salas
 */
public class RepoSala {
	
	private ArrayList<String> SQLScripts = new ArrayList<>();

	/**
	 * Constructor
	 */
	public RepoSala() {
		inicializarArray();
	}
	
	/**
	 * Inicializa el array de scripts
	 */
	private void inicializarArray() {
		
		// Insertar	0
		SQLScripts.add( 
				"INSERT "
					+ "sala "
				+ "VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, SHA2(pass = ?, 256))"
				);
		
		// Eliminar 1
		SQLScripts.add( 
				"DELETE "
					+ "cliente "
				+ "WHERE "
					+ "DNI = ?"
				);
		
		// Modificar 2		
		SQLScripts.add( 
				"UPDATE "
					+ "cliente "
				+ "SET "
					+ "DNI = ?, "
					+ "nom = ?, "
					+ "ape = ?, "
					+ "tlfno = ?, "
					+ "email = ?, "
					+ "bTrabajador = ?, "
					+ "tarifa = ?, "
					+ "SHA2(pass = ?, 256)"
				+ "WHERE "
					+ "DNI = ?"
				);
		
		// Comprobar existencia 3	
		SQLScripts.add( 
				"SELECT "
					+ "* "
				+ "FROM "
					+ "Cliente"
				+ "WHERE "
					+ "DNI = ?"
				);
		
		// Traer informarcion 4	
		SQLScripts.add( 
				"SELECT "
					+ "* "
				+ "FROM "
					+ "sala "
				+ "WHERE "
					+ "id = ? and "
					+ "num = ?;"
				);
		
		// Otros		
		
		// Tipos de habitaciones disponibles y su fecha mas cercana disponible. 5
		/*SQLScripts.add( 
				"SELECT "
					+ "h.tipohab, "
					+ "min(s.pvp) "
				+ "FROM "
					+ "habitacion h "
					+ "natural join sala s "
					+ "left join reserva r using(id, num) "
				+ "WHERE "
					+ "s.id = ? and "
					+ "r.fecfin > current_date() and "
					+ "r.fecIni not between ? and ? and "
					+ "r.fecFin not between ? and ? "
				+ "GROUP BY "
					+ "h.tipohab;"
				);
		*/
		SQLScripts.add("SELECT h.tipohab, MIN(s.pvp)\r\n"
				+ "FROM habitacion h\r\n"
				+ "NATURAL JOIN sala s\r\n"
				+ "LEFT JOIN reserva r USING(id, num)\r\n"
				+ "WHERE h.id = ?\r\n"
				+ "  AND (r.id IS NULL \r\n"
				+ "       OR (r.fecfin > CURRENT_DATE() \r\n"
				+ "           AND r.fecIni NOT BETWEEN ? AND ?\r\n"
				+ "           AND r.fecFin NOT BETWEEN ? AND ?)) AND"
				+ "h.num not in (?)\r\n"
				+ "GROUP BY h.tipohab;");
		
		SQLScripts.add( 
				"SELECT "
					+ "h.id, "
					+ "h.num "
				+ "FROM "
					+ "habitacion h "
					+ "natural join sala s "
					+ "left join reserva r using(id, num) "
				+ "WHERE "
					+ "s.id = ? and "
					+ "r.fecfin > current_date() and "
					+ "r.fecIni not between ? and ? and "
					+ "r.fecFin not between ? and ? and "
					+ "h.tipohab = ?;"
				);
	
	}

	/**
	 * Recupera la informacion de una sala
	 * @param id
	 * @param num
	 * @return
	 */
	public Sala get(int id, int num) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(this.SQLScripts.get(4))) {
			pS.setInt(1, id);
			pS.setInt(2, num);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				RepoHotel rH = new RepoHotel();
				Sala h = new Sala(
					rS.getInt(2),
					rS.getInt(3),
					rS.getString(4),
					rS.getDouble(5),
					rH.get(rS.getInt(1)),
					rS.getString(6)
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
	
	/**
	 * Recupera el menu de productos
	 * @return
	 */
	public ArrayList<Habitacion> getMenuProductos() {
		ArrayList<Habitacion> menuProductos = new ArrayList<>();
		//String query = SQLScripts.get(5);
		String query ="SELECT h.tipohab, MIN(s.pvp)\r\n"
				+ "FROM habitacion h\r\n"
				+ "NATURAL JOIN sala s\r\n"
				+ "LEFT JOIN reserva r USING(id, num)\r\n"
				+ "WHERE h.id = ?\r\n"
				+ "  AND (r.id IS NULL \r\n"
				+ "       OR (r.fecfin > CURRENT_DATE() \r\n"
				+ "           AND r.fecIni NOT BETWEEN ? AND ?\r\n"
				+ "           AND r.fecFin NOT BETWEEN ? AND ?)) AND "
				+ "h.num not in (";
		if(MenuCarrito.carrito.isEmpty()) {
			query += "\"\"";
		} else {
			for (int i = 0 ; i < MenuCarrito.carrito.size() ; i++) {
				query += (i+1 == MenuCarrito.carrito.size()) ? "? " : "?, ";
				
			}
		}
		query += ") GROUP BY h.tipohab;";
		
		
	    try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
	    	pS.setInt(1, MenuPrincipal.hotel.getID());
	    	pS.setDate(2, MenuProductos.fecIni);
	    	pS.setDate(3, MenuProductos.fecFin);
	    	pS.setDate(4, MenuProductos.fecIni);
	    	pS.setDate(5, MenuProductos.fecFin);
			if(!MenuCarrito.carrito.isEmpty()) {
		    	int i = 0;
		    	for (Reserva r : MenuCarrito.carrito) {
		    		i++;
		    		pS.setInt(i+5, r.getSala().getNum());
		    	}
			}
			
	    	ResultSet rS = pS.executeQuery();
	    	while (rS.next()) {
	    		Habitacion h = new Habitacion(MenuPrincipal.hotel, 0, 0, "",  rS.getDouble(2), rS.getString(1));
	    		menuProductos.add(h);
	    	}
	    	return menuProductos;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return null;
	}

	/**
	 * Recupera la sala selecionada en el menu de productos
	 * @param tipoHab
	 * @return
	 */
	public Habitacion getSeleccionMenuProductos(tipoHab tipoHab) {
		String query = SQLScripts.get(6);
	    try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
	    	pS.setInt(1, MenuPrincipal.hotel.getID());
	    	pS.setDate(2, MenuProductos.fecIni);
	    	pS.setDate(3, MenuProductos.fecFin);
	    	pS.setDate(4, MenuProductos.fecIni);
	    	pS.setDate(5, MenuProductos.fecFin);
	    	pS.setString(6, tipoHab.toString());
	    	ResultSet rS = pS.executeQuery();
	    	RepoHabitacion rH = new RepoHabitacion();
	    	rS.next();
	    	Habitacion h = rH.get(MenuPrincipal.hotel.getID(), rS.getInt(2));
	    	return h;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return null;
	}

	/**
	 * Inserta una nueva sala
	 * @param nuevo
	 * @return
	 */
	public boolean insert(Sala nuevo) {
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			if (nuevo.getClass().equals(Habitacion.class)) {
				nuevo.setTSala(tSala.Habitacion);
			} else if (nuevo.getClass().equals(EspacioComun.class)) {
				nuevo.setTSala(tSala.EspaciosComunes);				
			} else if (nuevo.getClass().equals(SalaReunion.class)) {
				nuevo.setTSala(tSala.SalaReuniones);
			}
			
			String query = "INSERT sala (id, num, capacidad, tlfno, pvp, subtipo) "
					+ "VALUES (?, ?, ?, ?, ?, ?);";
			
			//Si no existe el cliente, hace la consulta a la BBDD
	        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	            preparedStatement.setInt(1, nuevo.getHotel().getID());
	            preparedStatement.setInt(2, nuevo.getNum());
	            preparedStatement.setInt(3, nuevo.getCapacidad());
	            preparedStatement.setString(4, nuevo.getTlfno());
	            preparedStatement.setDouble(5, nuevo.getPvp());
	            preparedStatement.setString(6, nuevo.getTSala().toString());
	            preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        if (check(nuevo)) {
		        	System.out.print("\n~~~ Sala creada correctamente ~~~\n");
		        	return true;
		        } else {
		        	System.out.print("\n>>> Se ha producido un error <<<\n\n");
		        	return false;
		        }

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error al insertar la nueva sala");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		System.out.println("El usuario ya existe");
		return true;
	}

	/**
	 * Comprueba la existencia de una sala
	 * @param sala
	 * @return
	 */
	private boolean check(Sala sala) {

		String query = "SELECT * FROM sala "
				+ " WHERE id = ? and num = ?;";
		
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	        preparedStatement.setInt(1, sala.getHotel().getID());
			preparedStatement.setInt(2, sala.getNum());
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

	public boolean update(Sala modificaciones) {
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		Sala original = new Sala(0, 0, "", 0, null, "");
		
		// Copruebo que me han pasado el ID correcto
		if (modificaciones.getNum() != 0 && modificaciones.getHotel() != null) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getHotel().getID(), modificaciones.getNum());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (modificaciones.getCapacidad() != 0) original.setCapacidad(modificaciones.getCapacidad());
			if (!modificaciones.getTlfno().equals("")) original.setTlfno(modificaciones.getTlfno());
			if (modificaciones.getPvp() != 0) original.setPvp(modificaciones.getPvp());
		
		// En caso de no tener el DNI correcto devuelvo error
		} else {
			System.out.println("Error al insertar el Hotel");
			return false;
		}
		
		String query = "UPDATE sala "
				+ "SET capacidad = ?, tlfno = ?, pvp = ? "
				+ "WHERE id = ? and num = ?";
		
		//Si existe el cliente, ejecuta el borrado en la BBDD
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
	        pS.setInt(1, original.getCapacidad());
	        pS.setString(2, original.getTlfno());
	        pS.setDouble(3, original.getPvp());
	        pS.setInt(4, original.getHotel().getID());
	        pS.setInt(5, original.getNum());
	        pS.executeUpdate();
	        
	        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
	        return true;

		//En caso de que haya algun error en la base lo coge aqui
		} catch (SQLException e) {
			System.out.println("Error al actualizar la sala"+e);
			return false;
		}
	}
	
}
