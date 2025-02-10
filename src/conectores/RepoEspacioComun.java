package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.EspacioComun;
import model.Reserva;
import model.Sala;

public class RepoEspacioComun {

	public ArrayList<EspacioComun> getListaFiltrada(EspacioComun filtro) {
		String query = 
				"SELECT  "
					+ "R.FECINI, "
					+ "R.FECFIN, "
					+ "R.DNI, "
					+ "H.NOM, "
					+ "S.NUM, "
					+ "R.CODRESERVA "
				+ "FROM "
					+ "RESERVA R "
					+ "JOIN CLIENTE C USING(DNI) "
					+ "JOIN SALA S USING(ID, NUM) "
					+ "JOIN HOTEL H USING(ID) "
				+ "WHERE "
					+ "(h.nom = ? or ? = \"\") and "
					+ "(c.dni = ? or ? = \"\") and "
					+ "(r.num = ? or ? = 0) and "
					+ "(r.fecini >= ? or ? is null) and "
					+ "(r.fecini <= ? or ? is null) "
				+ "ORDER BY "
					+ "R.FECINI ASC, "
					+ "R.FECFIN ASC;";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			if (filtro.getSala() != null) {
				if (filtro.getSala().getHotel() != null) {
					pS.setString(1, filtro.getSala().getHotel().getNombre());
					pS.setString(2, filtro.getSala().getHotel().getNombre());
				} else {
					pS.setString(1, "");
					pS.setString(2, "");
				}
				pS.setInt(5, filtro.getSala().getNum());
				pS.setInt(6, filtro.getSala().getNum());
			} else {
				pS.setString(1, "");
				pS.setString(2, "");
				pS.setInt(5, 0);
				pS.setInt(6, 0);
			}
			
			if (filtro.getCliente() != null) {
				pS.setString(3, filtro.getCliente().getDNI());
				pS.setString(4, filtro.getCliente().getDNI());
			} else {
				pS.setString(3, "");
				pS.setString(4, "");
			}
				
			if (filtro.getFecIni() != null) {
				pS.setDate(7, filtro.getFecIni());
				pS.setDate(8, filtro.getFecIni());
			} else {
				pS.setDate(7, null);
				pS.setDate(8, null);
			}
			
			if (filtro.getFecFin() != null) {
				pS.setDate(9, filtro.getFecFin());
				pS.setDate(10, filtro.getFecFin());
			} else {
				pS.setDate(9, null);
				pS.setDate(10, null);
			}
			ResultSet rS = pS.executeQuery();
						
			ArrayList<Reserva> lista = new ArrayList<>();
			RepoCliente rC = new RepoCliente();
			RepoHotel rH = new RepoHotel();
			while (rS.next()) {
				Sala s = new Sala(
								rS.getInt(5), 
								0, 
								"", 
								0, 
								rH.get(rH.getPKByName(rS.getString(4))),
								""
							);
				Reserva r = new Reserva(
								rS.getInt(6),
								rS.getDate(1),
								rS.getDate(2),
								rC.get(rS.getString(3)),
								s,
								0
							);
				lista.add(r);
			}
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
