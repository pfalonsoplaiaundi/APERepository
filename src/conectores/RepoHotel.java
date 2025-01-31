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
		SQLScripts.add("SELECT nom FROM hotel;");
		
		// Obtener el PK de un hotel mediante el nombre del hotel 7
		SQLScripts.add("SELECT id FROM hotel WHERE nom = ?;");
	}

	
	
	/**
	 * Esta funcion inserta un cliente nuevo en la tabla cliente con todos los parametros de cliente
	 */
	public boolean insert(Cliente nuevo) {
		
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			//Si no existe el cliente, hace la consulta a la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
		        preparedStatement.setString(1, nuevo.getDNI());
		        preparedStatement.setString(2, nuevo.getNombre());
		        preparedStatement.setString(3, nuevo.getApellidos());
		        preparedStatement.setInt(4, nuevo.getTelefono());
		        preparedStatement.setString(5, nuevo.getEmail());
		        preparedStatement.setBoolean(6, nuevo.isbTrabajador());
		        preparedStatement.setString(9, nuevo.getTarifa().toString());
		        preparedStatement.setString(8, nuevo.getPass());
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

	public boolean delete(Cliente aBorrar) {
		
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
		        preparedStatement.setString(1, aBorrar.getDNI());
		        preparedStatement.setString(2, aBorrar.getNombre());
		        preparedStatement.setString(3, aBorrar.getApellidos());
		        preparedStatement.setInt(4, aBorrar.getTelefono());
		        preparedStatement.setString(5, aBorrar.getEmail());
		        preparedStatement.setBoolean(6, aBorrar.isbTrabajador());
		        preparedStatement.setString(9, aBorrar.getTarifa().toString());
		        preparedStatement.setString(8, aBorrar.getPass());
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
	public boolean update(Cliente modificaciones) {
				
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		Cliente original = new Cliente( "", "", "", 0, "", false, "");
		
		// Copruebo que me han pasado el DNI correcto
		if (!modificaciones.getDNI().equals("")) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getDNI());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (!modificaciones.getNombre().equals("")) original.setNombre(modificaciones.getNombre());
			if (!modificaciones.getApellidos().equals("")) original.setApellidos(modificaciones.getApellidos());
			if (!(modificaciones.getTelefono() == 0)) original.setTelefono(modificaciones.getTelefono());
			if (!modificaciones.getEmail().equals("")) original.setEmail(modificaciones.getEmail());
			if (!modificaciones.getTarifa().toString().equals("estandar")) original.setTarifa(modificaciones.getTarifa());
			if (!modificaciones.getPass().equals("")) original.setPass(modificaciones.getPass());
		
		// En caso de no tener el DNI correcto devuelvo error
		} else {
			System.out.println("Error al insertar el DNI");
			return false;
		}
				
		// Revisa si existe el cliente
		if(check(original)) {
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(2))) {
		        preparedStatement.setString(1, original.getDNI());
		        preparedStatement.setString(2, original.getNombre());
		        preparedStatement.setString(3, original.getApellidos());
		        preparedStatement.setInt(4, original.getTelefono());
		        preparedStatement.setString(5, original.getEmail());
		        preparedStatement.setBoolean(6, original.isbTrabajador());
		        preparedStatement.setString(9, original.getTarifa().toString());
		        preparedStatement.setString(8, original.getPass());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
		        return !checkEquals(original);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al actualizar el cliente");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}
	

	public boolean check(Cliente cliente) {
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
					rS.getInt(5),
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