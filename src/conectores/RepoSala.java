package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

import menu.MenuPrincipal;
import menu.MenuProductos;
import model.HabReserva;
import model.Habitacion;
import model.Sala;

public class RepoSala {
	
	private ArrayList<String> SQLScripts = new ArrayList<>();
	private int idHotel;
		
	public int getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}

	public RepoSala() {
		inicializarArray();
	}
	
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
					+ "num = ?"
				);
		
		// Otros		
		
		// Tipos de habitaciones disponibles y su fecha mas cercana disponible. 5
		SQLScripts.add( 
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
		
		SQLScripts.add("");
	
	}

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
	
	public ArrayList<Habitacion> getMenuProductos(int idHotel) {
		ArrayList<Habitacion> menuProductos = new ArrayList<>();
		String query = SQLScripts.get(5);
	    try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
	    	pS.setInt(1, idHotel);
	    	pS.setDate(2, MenuProductos.fecIni);
	    	pS.setDate(3, MenuProductos.fecFin);
	    	pS.setDate(4, MenuProductos.fecIni);
	    	pS.setDate(5, MenuProductos.fecFin);
	    	System.out.print(pS.toString());
	    	ResultSet rS = pS.executeQuery();
	    	while (rS.next()) {
	    		Habitacion h = new Habitacion(MenuPrincipal.hotel, 0, 0, "", rS.getDouble(2), rS.getString(1));
	    		menuProductos.add(h);
	    	}
	    	return menuProductos;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return null;
	}

}
