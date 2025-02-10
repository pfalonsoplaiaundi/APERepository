package conectores;

import java.sql.*;

public class ConectMySQL {

	//Conexion
	public static Connection conexion;
	
	/**
	 * Establece la conexión como usuario basico dentro de la app
	 */
	public static boolean conectar() {
	
		try {
			
			// Buscar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Cargando...");
			
			try {
				
				// Establecer conexion con la base de datos como usuario basico
				conexion = DriverManager.getConnection("jdbc:mysql://10.10.13.155:3306/hotel", "app", "app");
				
				System.out.println("Conexion establecida");
				return true;
			
			// Recoge los errores que pueda producirse en la conexion con la base de datos 
			} catch(Exception e) {
				System.out.println("Error en la conexion");
				return false;
			} 
			
		// Recoge los errores produccidos en la carga del driver	
		}catch(Exception e) {
			System.out.println("Error en el driver");
			return false;
		}
	}
	
	/**
	 * Establece la conexión como administrador dentro de la app
	 */
	public static boolean conectarAdm() {
		
		try {
			
			// Buscar el driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//System.out.println("Cargando...");
			
			try {
				
				// Establecer conexion con la base de datos como usuario basico
				conexion = DriverManager.getConnection("jdbc:mysql://10.10.13.155:3306/hotel", "appAdm", "appAdm");
				
				//System.out.println("Conexion establecida");
				return true;
			
			// Recoge los errores que pueda producirse en la conexion con la base de datos 
			} catch(Exception e) {
				System.out.println("Error en la conexion");
				return false;
			} 
			
		// Recoge los errores produccidos en la carga del driver	
		}catch(Exception e) {
			System.out.println("Error en el driver");
			return false;
		}
	}
}
