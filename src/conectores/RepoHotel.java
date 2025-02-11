package conectores;

import java.sql.*;
import java.util.ArrayList;

import model.*;

/**
 * Consultas a la bbdd de hoteles
 */
public class RepoHotel {

	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	/**
	 * Constructor del repositorio
	 */
	public RepoHotel() {
		inicializarArray();
	}
	
	/**
	 * Inicializa el array de scripts
	 * 0 Insert, 1 Delete, 2 Update, 3 Check, 4 Get, 5 NumOcupantesInHotel, 6 MenuPrincipal, 7 getPKHotelByName
	 */
	private void inicializarArray() {
		
		// Insertar	0
		SQLScripts.add( "INSERT hotel"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, SHA2(pass = ?, 256))"
				);
		
		// Eliminar 1
		SQLScripts.add( "DELETE cliente"
				+ "WHERE DNI = ?"
				);
		
		// Modificar 2		
		SQLScripts.add( "UPDATE cliente"
				+ "SET DNI = ?, nom = ?, ape = ?, tlfno = ?, email = ?, bTrabajador = ?, tarifa = ?, SHA2(pass = ?, 256)"
				+ "WHERE DNI = ?"
				);
		
		// Comprobar existencia 3	
		SQLScripts.add( "SELECT * FROM Cliente "
				+ "WHERE DNI = ?"
				);
		
		// Traer informarcion 4	
		SQLScripts.add( "SELECT * FROM hotel "
				+ "WHERE id = ?"
				);
		
		// Otros		
		
		// Numero de ocupantes de habitaciones en un hotel concreto. 5
		SQLScripts.add( "SELECT"
				+ "sum(capacidad) from hotel join sala using(id) natural join habitacion where id = ? group by nom;"
				);
		
		// Obtener el nombre de todos los hoteles 6
		SQLScripts.add("SELECT nom FROM hotel;"); // AÑADIR ID
		/*
		 * wip
		 */
		
		// Obtener el PK de un hotel mediante el nombre del hotel 7
		SQLScripts.add("SELECT id FROM hotel WHERE nom like ?;");
	}
	
	/**
	 * Esta funcion inserta un hotel nuevo en la tabla hotel 
	 * con todos los parametros de hotel, menos ID
	 * @param nuevo
	 * @return
	 */
	public boolean insert(Hotel nuevo) {
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			String query = 
					"INSERT "
						+ "Hotel (nom, ciu, dir, tlfno, email)"
					+ "VALUES "
						+ "(?, ?, ?, ?, ?) ";
			
			//Si no existe el cliente, hace la consulta a la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
		        preparedStatement.setString(1, nuevo.getNombre());
		        preparedStatement.setString(2, nuevo.getCiudad());
		        preparedStatement.setString(3, nuevo.getDir());
		        preparedStatement.setString(4, nuevo.getTlfno());
		        preparedStatement.setString(5, nuevo.getEmail());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        return check(nuevo);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al insertar el nuevo hotel");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	/**
	 * Esta funcion borra un hotel en la tabla hotel 
	 * con todos los parametros de hotel
	 * @param aBorrar
	 * @return
	 */
	public boolean delete(Hotel aBorrar) {
				
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si existe el cliente
		if(check(aBorrar)) {
			
			String query = 
					"DELETE FROM "
						+ "hotel "
					+ "WHERE "
						+ "id = ?";
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
				preparedStatement.setInt(1, aBorrar.getID());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve lo contrario en funcion de esta
		        return true;

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al eliminar el cliente");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	/**
	 * Esta funcion modifica un hotel
	 * @param modificaciones
	 * @return
	 */
	public boolean update(Hotel modificaciones) {
				
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		Hotel original = new Hotel(0, "", "", "", "", "");
		
		// Copruebo que me han pasado el ID correcto
		if (modificaciones.getID() != 0) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getID());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (!modificaciones.getNombre().equals("")) original.setNombre(modificaciones.getNombre());
			if (!modificaciones.getCiudad().equals("")) original.setCiudad(modificaciones.getCiudad());
			if (!modificaciones.getDir().equals("")) original.setDir(modificaciones.getDir());
			if (!modificaciones.getTlfno().equals("")) original.setTlfno(modificaciones.getTlfno());
			if (!modificaciones.getEmail().equals("")) original.setEmail(modificaciones.getEmail());
		
		// En caso de no tener el DNI correcto devuelvo error
		} else {
			System.out.println("Error al insertar el Hotel");
			return false;
		}
		
		String query = "UPDATE hotel "
				+ "SET id = ?, nom = ?, ciu = ?, dir = ?, tlfno = ?, email = ? "
				+ "WHERE id = ?";
		
		//Si existe el cliente, ejecuta el borrado en la BBDD
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	        preparedStatement.setInt(1, original.getID());
	        preparedStatement.setString(2, original.getNombre());
	        preparedStatement.setString(3, original.getCiudad());
	        preparedStatement.setString(4, original.getDir());
	        preparedStatement.setString(5, original.getTlfno());
	        preparedStatement.setString(6, original.getEmail());
	        preparedStatement.setInt(7, original.getID());
	        preparedStatement.executeUpdate();
	        
	        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
	        return true;

		//En caso de que haya algun error en la base lo coge aqui
		} catch (SQLException e) {
			System.out.println("Error al actualizar el Hotel"+e);
			return false;
		}
		
	}
	
	/**
	 * Comprueba la existencia de un hotel
	 * @param hotel
	 * @return
	 */
	public boolean check(Hotel hotel) {
    	if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
    	String query = "SELECT * FROM hotel "
				+ "WHERE id = ?";
    	
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	        preparedStatement.setInt(1, hotel.getID());
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
	 * Recupera la informacion de un hotel
	 * @param idHotel
	 * @return
	 */
	public Hotel get(int idHotel) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(this.SQLScripts.get(4))) {
			pS.setInt(1, idHotel);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				Hotel c = new Hotel(
					rS.getInt(1),
					rS.getString(2),
					rS.getString(3),
					rS.getString(4),
					rS.getString(5),
					rS.getString(6)
				);
				return c;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Recupera la capacidad de aforo de las habitaciones de un hotel
	 * @param id
	 * @return
	 */
	public static int getTamano(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * Recupera la informacion necesaria para hacer el menu principal
	 * @return
	 */
	public ArrayList<String> getMenuPrincipal() {
		ArrayList<String> mP = new ArrayList<>();
		String query = SQLScripts.get(6);
	    try (
	    	PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query);
	    	ResultSet resultSet = pS.executeQuery(query)
	    ) {
	    	while (resultSet.next()) {
	       		mP.add(resultSet.getString("nom"));
	       	} 
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return mP;
	}

	/**
	 * Recupera el id de un hotel mediante su nombre ya que es unico
	 * @param nom
	 * @return
	 */
	public int getPKByName(String nom) {
		try ( 
			PreparedStatement pS = ConectMySQL.conexion.prepareStatement(SQLScripts.get(7));
		) { 
			pS.setString(1, nom);	
			ResultSet rS = pS.executeQuery();
			rS.next();
			return rS.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * Recupera una lista de hoteles filtrando
	 * @param filtro
	 * @return
	 */
	public ArrayList<Hotel> getListaFiltrada(Hotel filtro) {
		String query = 
				"SELECT  "
					+ "h.nom, "
					+ "h.ciu, "
					+ "h.dir, "
					+ "h.tlfno, "
					+ "h.email, "
					+ "h.id "
				+ "FROM "
					+ "Hotel h "
				+ "WHERE "
					+ "(h.nom = ? or ? = \"\") and "
					+ "(h.tlfno = ? or ? = \"\") and "
					+ "(h.email = ? or ? = \"\") and "
					+ "(h.ciu = ? or ? = \"\") "
				+ "ORDER BY "
					+ "h.nom ASC, "
					+ "h.id ASC;";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			
			if (!filtro.getNombre().equals("")) {
				pS.setString(1, filtro.getNombre());
				pS.setString(2, filtro.getNombre());
			} else {
				pS.setString(1, "");
				pS.setString(2, "");
			}
			
			if (!filtro.getTlfno().equals("")) {
				pS.setString(3, filtro.getTlfno());
				pS.setString(4, filtro.getTlfno());
			} else {
				pS.setString(3, "");
				pS.setString(4, "");
			}
			
			if (!filtro.getEmail().equals("")) {
				pS.setString(5, filtro.getEmail());
				pS.setString(6, filtro.getEmail());
			} else {
				pS.setString(5, "");
				pS.setString(6, "");
			}
			
			if (!filtro.getCiudad().equals("")) {
				pS.setString(7, filtro.getCiudad());
				pS.setString(8, filtro.getCiudad());
			} else {
				pS.setString(7, "");
				pS.setString(8, "");
			}
			
			ResultSet rS = pS.executeQuery();
						
			ArrayList<Hotel> lista = new ArrayList<>();
			while (rS.next()) {
				Hotel ho = new Hotel(
						rS.getInt(6),
						rS.getString(1),
						rS.getString(2),
						rS.getString(3),
						rS.getString(4),
						rS.getString(5)
						);
				lista.add(ho);
			}
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}