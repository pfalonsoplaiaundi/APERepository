package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import menu.MenuCarrito;
import menu.MenuPrincipal;
import menu.MenuProductos;
import model.Habitacion;
import model.Habitacion.tipoHab;
import model.Reserva;
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
		/*SQLScripts.add( 
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
		*/
		SQLScripts.add("SELECT h.tipohab, MIN(s.pvp)\r\n"
				+ "FROM habitacion h\r\n"
				+ "NATURAL JOIN sala s\r\n"
				+ "LEFT JOIN reserva r USING(id, num)\r\n"
				+ "WHERE h.id = ?\r\n"
				+ "  AND (r.id IS NULL \r\n"
				+ "       OR (r.fecfin > CURRENT_DATE() \r\n"
				+ "           AND r.fecIni NOT BETWEEN ? AND ?\r\n"
				+ "           AND r.fecFin NOT BETWEEN ? AND ?)) AND"
				+ "h.num not in (?)\r\n"
				+ "GROUP BY h.tipohab;");
		
		SQLScripts.add( 
				"SELECT "
					+ "h.id, "
					+ "h.num "
				+ "FROM "
					+ "habitacion h "
					+ "natural join sala s "
					+ "left join reserva r using(id, num) "
				+ "WHERE "
					+ "s.id = ? and "
					+ "r.fecfin > current_date() and "
					+ "r.fecIni not between ? and ? and "
					+ "r.fecFin not between ? and ? and "
					+ "h.tipohab = ?;"
				);
	
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
	
	public ArrayList<Habitacion> getMenuProductos() {
		ArrayList<Habitacion> menuProductos = new ArrayList<>();
		//String query = SQLScripts.get(5);
		String query ="SELECT h.tipohab, MIN(s.pvp)\r\n"
				+ "FROM habitacion h\r\n"
				+ "NATURAL JOIN sala s\r\n"
				+ "LEFT JOIN reserva r USING(id, num)\r\n"
				+ "WHERE h.id = ?\r\n"
				+ "  AND (r.id IS NULL \r\n"
				+ "       OR (r.fecfin > CURRENT_DATE() \r\n"
				+ "           AND r.fecIni NOT BETWEEN ? AND ?\r\n"
				+ "           AND r.fecFin NOT BETWEEN ? AND ?)) AND "
				+ "h.num not in (";
		if(MenuCarrito.carrito.isEmpty()) {
			query += "\"\"";
		} else {
			for (int i = 0 ; i < MenuCarrito.carrito.size() ; i++) {
				query += (i == MenuCarrito.carrito.size()) ? "? " : "?, ";
				
			}
		}
		query += ") GROUP BY h.tipohab;";
		
		
	    try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
	    	pS.setInt(1, MenuPrincipal.hotel.getID());
	    	pS.setDate(2, MenuProductos.fecIni);
	    	pS.setDate(3, MenuProductos.fecFin);
	    	pS.setDate(4, MenuProductos.fecIni);
	    	pS.setDate(5, MenuProductos.fecFin);
			if(!MenuCarrito.carrito.isEmpty()) {
		    	int i = 0;
		    	for (Reserva r : MenuCarrito.carrito) {
		    		i++;
		    		pS.setInt(i+5, r.getSala().getNum());
		    	}
			}
			// System.out.print("\n" + pS.toString() + "\n");
	    	ResultSet rS = pS.executeQuery();
	    	while (rS.next()) {
	    		Habitacion h = new Habitacion(MenuPrincipal.hotel, 0, 0, "",  rS.getDouble(2), rS.getString(1));
	    		menuProductos.add(h);
	    	}
	    	return menuProductos;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return null;
	}

	public Habitacion getSeleccionMenuProductos(tipoHab tipoHab) {
		String query = SQLScripts.get(6);
	    try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
	    	pS.setInt(1, MenuPrincipal.hotel.getID());
	    	pS.setDate(2, MenuProductos.fecIni);
	    	pS.setDate(3, MenuProductos.fecFin);
	    	pS.setDate(4, MenuProductos.fecIni);
	    	pS.setDate(5, MenuProductos.fecFin);
	    	pS.setString(6, tipoHab.toString());
	    	ResultSet rS = pS.executeQuery();
	    	RepoHabitacion rH = new RepoHabitacion();
	    	rS.next();
	    	Habitacion h = rH.get(MenuPrincipal.hotel.getID(), rS.getInt(2));
	    	return h;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return null;
	}
	
}
