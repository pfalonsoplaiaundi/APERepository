package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import menu.MenuCarrito;
import menu.MenuPrincipal;
import menu.MenuProductos;
import model.HabDisponible;
import model.Habitacion;
import model.Reserva;

/**
 * Consultas de la BBDD de habitaciones
 */
public class RepoHabitacion {
	
	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	/**
	 * Constructor del repositorio
	 */
	public RepoHabitacion() {
		inicializarArray();
	}
	
	/**
	 * Inicializa el array de scripts
	 */
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
				+ " WHERE id = ? and num = ?;"
				);
		
		// Otros
		// Recuperar la habitacion libre mas cercana de un tipo 5
		this.SQLScripts.add(
				"SELECT "
					+ "s.num, "
					+ "s.capacidad, "
					+ "s.tlfno, "
					+ "s.pvp, "
					+ "h.tipohab "
				+ "FROM "
					+ "habitacion h "
					+ "JOIN sala s USING(id, num) "
					+ "LEFT JOIN reserva r ON "
						+ "r.id = s.id AND "
						+ "r.num = s.num AND "
						+ "? BETWEEN r.FecIni AND r.FecFin AND"
						+ "? BETWEEN r.fecini and r.fecfin "
				+ "WHERE "
					+ "r.id IS null AND "
					+ "h.tipohab = ? AND "
					+ "h.id = ? "
				+ "ORDER BY "
					+ "s.pvp ASC;"
				);
	}
	
	/**
	 * Inserta una habitacion nueva
	 * @param nuevo
	 * @return
	 */
	public boolean insert(Habitacion nuevo) {
			
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			RepoSala rSa = new RepoSala();
			rSa.insert(nuevo);
			
			String query = "INSERT INTO habitacion (id, num, tipohab) "
					+ "VALUES "
					+ "(?, ?, ?)";
			
			//Si no existe el cliente, hace la consulta a la BBDD
	        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	            preparedStatement.setInt(1, nuevo.getHotel().getID());
	            preparedStatement.setInt(2, nuevo.getNum());
	            preparedStatement.setString(3, nuevo.getTipo().toString());
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
	 * Elimina una habitacion de la bbdd
	 * @param aBorrar
	 * @return
	 */
	public boolean delete(Habitacion aBorrar) {
				
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si existe el cliente
		if(check(aBorrar)) {
			
			String query = "DELETE FROM sala "
					+ "WHERE id = ? and num = ?;";
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
	            pS.setInt(1, aBorrar.getHotel().getID());
				pS.setInt(2, aBorrar.getNum());
				
	            pS.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve lo contrario en funcion de esta
		        return true;

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al eliminar la habitacion" + e);
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	/**
	 * Modifica una habitacion de la base de datos
	 * @param modificaciones
	 * @return
	 */
	public boolean update(Habitacion modificaciones) {
		
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
			System.out.println("Error al insertar la sala");
			return false;
		}
				
		// Revisa si existe el cliente
		if(check(original)) {
			
			RepoSala rSa = new RepoSala();
			rSa.update(original);
			
			String query = "UPDATE habitacion "
					+ "SET tipoHab = ? "
					+ " WHERE id = ? and num = ?;";
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
				preparedStatement.setString(1, original.getTipo().toString());
				preparedStatement.setInt(2, original.getHotel().getID());
				preparedStatement.setInt(3, original.getNum());
				
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
		        return true;

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al actualizar la habitacion" + e);
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}
	
	/**
	 * Comprueba la existencia de una habitacion
	 * @param habitacion
	 * @return
	 */
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

	/**
	 * Recuperas los datos de una habitacion
	 * @param idHotel
	 * @param num
	 * @return
	 */
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
	
	/**
	 * Busca la habitacion libre de un tipo mas cercana
	 * @param tipoDeHab
	 * @return
	 */
	public Habitacion getByTypeAndFirstDate(String tipoDeHab) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		String query = "SELECT "
					+ "s.num, "
					+ "s.capacidad, "
					+ "s.tlfno, "
					+ "s.pvp, "
					+ "h.tipohab "
				+ "FROM "
					+ "habitacion h "
					+ "JOIN sala s USING(id, num) "
					+ "LEFT JOIN reserva r ON "
						+ "r.id = s.id AND "
						+ "r.num = s.num AND "
						+ "? BETWEEN r.FecIni AND r.FecFin AND"
						+ "? BETWEEN r.fecini and r.fecfin "
				+ "WHERE "
					+ "r.id IS null AND "
					+ "h.tipohab = ? AND "
					+ "h.id = ? AND "
					+ "h.num not in (";
		if(MenuCarrito.carrito.isEmpty()) {
			query += "\"\"";
		} else {
			for (int i = 0 ; i < MenuCarrito.carrito.size(); i++) {
				query += (i+1 == MenuCarrito.carrito.size()) ? "? " : "?, ";
				
			}
		}	
		query	+= ") ORDER BY "
					+ "s.pvp ASC;"
				;
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			pS.setDate(1, MenuProductos.fecIni);
			pS.setDate(2, MenuProductos.fecFin);
			pS.setString(3, tipoDeHab);
			pS.setInt(4, MenuPrincipal.hotel.getID());
			if(!MenuCarrito.carrito.isEmpty()) {
		    	int i = 0;
		    	for (Reserva r : MenuCarrito.carrito) {
		    		i++;
		    		pS.setInt(i+4, r.getSala().getNum());
		    	}
			}
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

	/**
	 * Recupera la informacion de las habitaciones y su disponibilidad actual segun un filtro
	 * @param filtro
	 * @param disponible
	 * @return
	 */
	public ArrayList<HabDisponible> getListaFiltrada(Habitacion filtro, int disponible) {
		String query = 
				"SELECT  "
					+ "h.nom, "
					+ "s.num, "
					+ "s.capacidad, "
					+ "s.pvp, "
					+ "s.tlfno, "
					+ "ha.tipohab, "
					+ "case when current_date() between r.fecini and r.fecfin then true else false end "
				+ "FROM "
					+ "habitacion ha "
					+ "JOIN sala s USING(id, num) "
					+ "JOIN HOTEL H USING(ID) "
					+ "LEFT JOIN reserva r USING(id, num) "
				+ "WHERE "
					+ "(h.nom = ? or ? = \"\") and "
					+ "(h.ciu = ? or ? = \"\") "
				+ "ORDER BY "
					+ "h.id ASC, "
					+ "s.num ASC;";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			
			if (filtro.getHotel() != null && !filtro.getHotel().getNombre().equals("")) {
				pS.setString(1, filtro.getHotel().getNombre());
				pS.setString(2, filtro.getHotel().getNombre());
			} else {
				pS.setString(1, "");
				pS.setString(2, "");
			}
				
			if (filtro.getHotel() != null && !filtro.getHotel().getCiudad().equals("")) {
				pS.setString(3, filtro.getHotel().getCiudad());
				pS.setString(4, filtro.getHotel().getCiudad());
			} else {
				pS.setString(3, "");
				pS.setString(4, "");
			}
			
			ResultSet rS = pS.executeQuery();
						
			ArrayList<HabDisponible> lista = new ArrayList<>();
			RepoHotel rH = new RepoHotel();
			while (rS.next()) {
				Habitacion h = new Habitacion(
						rH.get(rH.getPKByName(rS.getString(1))),
						rS.getInt(2),
						rS.getInt(3),
						rS.getString(5),
						rS.getDouble(4),
						rS.getString(6)
						);

				HabDisponible hd = new HabDisponible(h, rS.getBoolean(7));
				if (disponible == -1) {
					lista.add(hd);
				} else if (disponible == 1) {
					if (hd.isDisponible()) lista.add(hd);
				} else if (disponible == 0) {
					if (!hd.isDisponible()) lista.add(hd); 
				}
			}
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}


}
