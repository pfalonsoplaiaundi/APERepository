package conectores;

import java.util.ArrayList;
import java.sql.*;

public class RepoReserva {

	private ArrayList<String> SQLScripts = new ArrayList<>();	
	
	public RepoReserva() {
		inicializarArray();
	}
	
	private void inicializarArray() {
		
		// Insertar	0
		this.SQLScripts.add( "INSERT INTO Reserva (DNI, id, num, fecini, fecfin) VALUES\r\n"
				+ "(?, ?, ?, ?, ?)"
				);
		
		// Eliminar 1
		this.SQLScripts.add( "DELETE reserva"
				+ "WHERE codreserva = ?"
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
		
		// Obtener el ultimo codreserva 5
		this.SQLScripts.add( "SELECT max(codreserva) "
				+ "from reserva;"
				);
		
		this.SQLScripts.add("");
	}

	public int getNewID() {
		if (SQLScripts.isEmpty()) {
			inicializarArray();
		}
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareCall(SQLScripts.get(5))) {
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				return rS.getInt(1);
			} else {
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
}