package conectores;

import java.sql.*;
import java.util.ArrayList;

import model.*;
import model.Cliente.tarifas;

public class RepoHotel {

	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	private void inicializarArray() {
		
		// Insertar	
		SQLScripts.add( "INSERT hotel"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, SHA2(pass = ?, 256))"
				);
		
		// Eliminar
		SQLScripts.add( "DELETE cliente"
				+ "WHERE DNI = ?"
				);
		
		// Modificar		
		SQLScripts.add( "UPDATE cliente"
				+ "SET DNI = ?, nom = ?, ape = ?, tlfno = ?, email = ?, bTrabajador = ?, tarifa = ?, SHA2(pass = ?, 256)"
				+ "WHERE DNI = ?"
				);
		
		// Comprobar existencia	
		SQLScripts.add( "SELECT * FROM Cliente"
				+ "WHERE DNI = ?"
				);
		
		// Traer informarcion		
		SQLScripts.add( "SELECT * FROM Cliente"
				+ "WHERE DNI = ?"
				);
		
		// Otros		
		
		// Numero de ocupantes de habitaciones en un hotel concreto.
		SQLScripts.add( "SELECT"
				+ "sum(capacidad) from hotel join sala using(id) natural join habitacion where id = ? group by nom;"
				);
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

	public boolean update(Cliente modificaciones) {
		
		/**
		 * Esta funcion modifica un cliente nuevo en la tabla cliente con todos los parametros de cliente
		 */
		
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

	public Cliente get(String DNI) {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		return null;
	}
	
	public static int getTamano(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	

}