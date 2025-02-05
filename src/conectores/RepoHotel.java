package conectores;

import java.sql.*;
import java.util.ArrayList;

import model.*;
import model.Cliente.tarifas;

public class RepoHotel {

	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	public RepoHotel() {
		inicializarArray();
	}
	
	/**
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
	 * Esta funcion inserta un cliente nuevo en la tabla cliente con todos los parametros de cliente
	 */
	public boolean insert(Hotel nuevo) {
		
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			//Si no existe el cliente, hace la consulta a la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
				preparedStatement.setInt(1, nuevo.getID());
		        preparedStatement.setString(2, nuevo.getNombre());
		        preparedStatement.setString(3, nuevo.getCiudad());
		        preparedStatement.setString(4, nuevo.getDir());
		        preparedStatement.setString(5, nuevo.getTlfno());
		        preparedStatement.setString(9, nuevo.getEmail());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        return check(nuevo);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al insertar el nuevo cliente");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	public boolean delete(Hotel aBorrar) {
		
		/**
		 * Esta funcion borra un cliente nuevo en la tabla cliente con todos los parametros de cliente
		 */
		
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si existe el cliente
		if(check(aBorrar)) {
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(1))) {
				preparedStatement.setInt(1, aBorrar.getID());
		        preparedStatement.setString(2, aBorrar.getNombre());
		        preparedStatement.setString(3, aBorrar.getCiudad());
		        preparedStatement.setString(4, aBorrar.getDir());
		        preparedStatement.setString(5, aBorrar.getTlfno());
		        preparedStatement.setString(9, aBorrar.getEmail());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve lo contrario en funcion de esta
		        return !check(aBorrar);

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
	 * Esta funcion modifica un cliente nuevo en la tabla cliente con todos los parametros de cliente
	 */
	public boolean update(Hotel modificaciones) {
				
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		Hotel original = new Hotel(0, "", "", "", "", "");
		
		// Copruebo que me han pasado el DNI correcto
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
			System.out.println("Error al insertar el DNI");
			return false;
		}
				
		// Revisa si existe el cliente
		if(check(original)) {
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(2))) {
		        preparedStatement.setInt(1, original.getID());
		        preparedStatement.setString(2, original.getNombre());
		        preparedStatement.setString(3, original.getCiudad());
		        preparedStatement.setString(4, original.getDir());
		        preparedStatement.setString(5, original.getTlfno());
		        preparedStatement.setString(9, original.getEmail());
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
	

	public boolean check(Hotel hotel) {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		return false;
	}
	
	public boolean checkEquals(Cliente cliente) {
		return false;
	}

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
	
	public static int getTamano(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
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
	

}