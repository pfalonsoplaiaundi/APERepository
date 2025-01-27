package conectores;

import java.sql.*;
import java.util.ArrayList;

import model.*;

public class RepoCliente {

	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	private void inicializarArray() {
		
		// Insertar	
		SQLScripts.add( "INSERT cliente"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
				);
		
		// Eliminar
		SQLScripts.add( "DELETE cliente"
				+ "WHERE DNI = ?"
				);
		
		// Modificar		
		SQLScripts.add( "UPDATE cliente"
				+ "SET DNI = ?, nom = ?, ape = ?, tlfno = ?, email = ?, bTrabajador = ?, tarifa = ?, pass = ?"
				+ "WHERE DNI = ?, nom = ?, ape = ?, tlfno = ?, email = ?, bTrabajador = ?, tarifa = ?, pass = ?"
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
		SQLScripts.add( ""
				);
	}
	
	public boolean insert(Cliente nuevo) {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		if(!check(nuevo)) {
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(SQLScripts.get(0))) {
		        preparedStatement.setString(1, nuevo.DNI);
		        preparedStatement.setString(2, nuevo.nombre);
		        preparedStatement.setInt(3, nuevo.apellidos);
		        preparedStatement.setString(4, nuevo.telefono);
		        preparedStatement.setInt(5, nuevo.email);
		        preparedStatement.setString(6, nuevo.bTrabajador);
		        preparedStatement.setInt(7, nuevo.tarifa);
		        preparedStatement.setInt(8, nuevo.pass);
		        preparedStatement.executeUpdate();
			}
		}
		
		
		
		return false;
	}

	public boolean delete(Cliente aBorrar) {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		return false;
	}

	public boolean update(Cliente modificaciones) {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		return false;
	}

	public boolean check(Cliente objeto) {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		return false;
	}

	public Object get(String DNI) {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		return null;
	}


}
