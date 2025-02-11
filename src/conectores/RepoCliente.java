package conectores;

import java.sql.*;
import java.util.ArrayList;

import model.*;

/**
 * Consultas a la base de datos de cliente
 */
public class RepoCliente {

	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	/**
	 * Constructor del repocliente
	 */
	public RepoCliente() {
		inicializarArray();
	}
	
	/**
	 * Inicializacion del array de scripts
	 */
	private void inicializarArray() {
		
		// Insertar	0
		this.SQLScripts.add( "INSERT INTO Cliente (DNI, nom, ape, tlfno, email, btrabajador, tarifa, pass) VALUES\r\n"
				+ "(?, ?, ?, ?, ?, ?, ?, SHA2(?, 256))"
				);
		
		// Eliminar 1
		this.SQLScripts.add( "DELETE cliente"
				+ "WHERE DNI = ?"
				);
		
		// Modificar 2		
		this.SQLScripts.add( "UPDATE cliente "
				+ "SET DNI = ?, nom = ?, ape = ?, tlfno = ?, email = ?, bTrabajador = ?, tarifa = ?, SHA2(pass = ?, 256)"
				+ " WHERE DNI = ?"
				);
		
		// Comprobar existencia	3
		this.SQLScripts.add( "SELECT dni FROM Cliente"
				+ " WHERE DNI = ?;"
				);
		
		// Traer informarcion 4	
		this.SQLScripts.add( "SELECT * FROM Cliente"
				+ " WHERE DNI = ?"
				);
		
		// Otros
		
		// Comprobacion de credenciales 5
		this.SQLScripts.add( "SELECT c.dni, c.pass\r\n"
				+ "from cliente c\r\n"
				+ "where c.dni = ? and c.pass = sha2(?, 256);"
				);
		
	}
	
	/**
	 * Esta funcion inserta un cliente nuevo en la tabla cliente con todos los parametros de cliente. Devuelve true siempre que el usuario exista o haya sido insertado.
	 */
	public boolean insert(Cliente nuevo) {
			
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			//Si no existe el cliente, hace la consulta a la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
		        preparedStatement.setString(1, nuevo.getDNI());
		        preparedStatement.setString(2, nuevo.getNombre());
		        preparedStatement.setString(3, nuevo.getApellidos());
		        preparedStatement.setString(4, nuevo.getTelefono());
		        preparedStatement.setString(5, nuevo.getEmail());
		        preparedStatement.setBoolean(6, nuevo.isbTrabajador());
		        preparedStatement.setString(7, nuevo.getTarifa().toString());
		        preparedStatement.setString(8, nuevo.getPass());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        if (check(nuevo)) {
		        	System.out.print("\n~~~ Cuenta creada correctamente ~~~\n");
		        	return true;
		        } else {
		        	System.out.print("\n>>> Se ha producido un error <<<\n\n");
		        	return false;
		        }

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error al insertar el nuevo cliente");
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
	public boolean delete(Cliente aBorrar) {
				
		// Comprueba que los scrpits estan en el array y si no esta lo inicializa
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		// Revisa si existe el cliente
		if(check(aBorrar)) {
			
			String query =  
					"DELETE FROM "
						+ "cliente "
					+ "WHERE "
						+ "DNI = ?";
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
		        preparedStatement.setString(1, aBorrar.getDNI());
		        
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve lo contrario en funcion de esta
		        return !check(aBorrar);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al eliminar el cliente\n");
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
		Cliente original = new Cliente( "", "", "", "", "", false, "");
		
		// Copruebo que me han pasado el DNI correcto
		if (!modificaciones.getDNI().equals("")) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getDNI());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (!modificaciones.getNombre().equals("")) original.setNombre(modificaciones.getNombre());
			if (!modificaciones.getApellidos().equals("")) original.setApellidos(modificaciones.getApellidos());
			if (!modificaciones.getTelefono().equals("")) original.setTelefono(modificaciones.getTelefono());
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
		        preparedStatement.setString(4, original.getTelefono());
		        preparedStatement.setString(5, original.getEmail());
		        preparedStatement.setBoolean(6, original.isbTrabajador());
		        preparedStatement.setString(9, original.getTarifa().toString());
		        preparedStatement.setString(8, original.getPass());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
		        return true;

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al actualizar el cliente");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}
	
	/**
	 * Comprueba la existencia en la base de datos de un cliente
	 * @param cliente
	 * @return
	 */
	public boolean check(Cliente cliente) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(3))) {
	        preparedStatement.setString(1, cliente.getDNI());
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
	 * Consigue los datos de un cliente de la base de datos
	 * @param DNI
	 * @return
	 */
	public Cliente get(String DNI) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(this.SQLScripts.get(4))) {
			pS.setString(1, DNI);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				Cliente c = new Cliente(
					rS.getString(1),
					rS.getString(2),
					rS.getString(3),
					rS.getString(4),
					rS.getString(5),
					rS.getBoolean(6),
					rS.getString(7),
					rS.getString(8)
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
	 * Comprueba las credenciales de un cliente sean correctas
	 * @param user
	 * @param pass
	 * @return
	 */
	public boolean checkCreden(String user, String pass) {
		if (this.SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(this.SQLScripts.get(5))) {
	        pS.setString(1, user);
	        pS.setString(2, pass);
	        ResultSet rS = pS.executeQuery();
	        if (rS.next()) {
	        	return true;
	        } else {
	        	return false;
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Devuelve un array con los clientes filtrada por las necesidades
	 * @param filtro
	 * @param trabajador
	 * @return
	 */
	public ArrayList<Cliente> getListaFiltrada(Cliente filtro, int trabajador) {
		String query = 
				"SELECT  "
					+ "c.nom, "
					+ "c.ape, "
					+ "c.DNI, "
					+ "c.tlfno, "
					+ "c.email, "
					+ "c.btrabajador, "
					+ "c.tarifa "
				+ "FROM "
					+ "cliente c "
				+ "WHERE "
					+ "(c.nom = ? or ? = \"\") and "
					+ "(c.tlfno = ? or ? = \"\") and "
					+ "(c.email = ? or ? = 0) "
				+ "ORDER BY "
					+ "c.nom ASC, "
					+ "c.ape ASC;";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			
			if (!filtro.getNombre().equals("")) {
				pS.setString(1, filtro.getNombre());
				pS.setString(2, filtro.getNombre());
			} else {
				pS.setString(1, "");
				pS.setString(2, "");
			}
			
			if (!filtro.getTelefono().equals("")) {
				pS.setString(3, filtro.getTelefono());
				pS.setString(4, filtro.getTelefono());
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
			
			ResultSet rS = pS.executeQuery();
						
			ArrayList<Cliente> lista = new ArrayList<>();
			while (rS.next()) {
				Cliente c = new Cliente(
						rS.getString(3),
						rS.getString(1),
						rS.getString(2),
						rS.getString(4),
						rS.getString(5),
						rS.getBoolean(6),
						rS.getString(7),
						""
						);
				if (trabajador == 1 && c.isbTrabajador()) { // Si se quieren los trabajadores solo meto trabajadores
					lista.add(c);
				} else if (trabajador == 0 && !c.isbTrabajador()) { // Si se quieren los clientes solo meto los clientes
					lista.add(c);
				} else if (trabajador == -1) { // Si me da igual meto todos
					lista.add(c);
				}
			}
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	/**
	 * Recupera de la base de datos todos los clientes
	 * @return
	 */
	public ArrayList<Cliente> getLista() {
		
		String query = 
				"SELECT  "
					+ "c.nom, "
					+ "c.ape, "
					+ "c.DNI, "
					+ "c.tlfno, "
					+ "c.email, "
					+ "c.btrabajador, "
					+ "c.tarifa "
				+ "FROM "
					+ "cliente c "
				+ "ORDER BY "
					+ "c.nom ASC, "
					+ "c.ape ASC;";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			
			ResultSet rS = pS.executeQuery();
						
			ArrayList<Cliente> lista = new ArrayList<>();
			while (rS.next()) {
				Cliente c = new Cliente(
						rS.getString(3),
						rS.getString(1),
						rS.getString(2),
						rS.getString(4),
						rS.getString(5),
						rS.getBoolean(6),
						rS.getString(7),
						""
						);
				lista.add(c);
			}
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
