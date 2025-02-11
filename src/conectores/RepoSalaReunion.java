package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.SalaReunion;

/**
 * Repositorio de consultas de salas de reunion
 */
public class RepoSalaReunion {

	/**
	 * Elimina una sala de reunion
	 * @param aBorrar
	 * @return
	 */
	public boolean delete(SalaReunion aBorrar) {
		
		// Revisa si existe el cliente
		if(check(aBorrar)) {

			String query = "DELETE FROM espacioscomunes "
					+ "WHERE id = ? and num = ?";
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	            preparedStatement.setInt(1, aBorrar.getHotel().getID());
	            preparedStatement.setInt(2, aBorrar.getNum());
	            preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve lo contrario en funcion de esta
		        return !check(aBorrar);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al eliminar el espacio comun");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	/**
	 * Comprueba la existencia de una sala de reunion
	 * @param salaReunion
	 * @return
	 */
	public boolean check(SalaReunion salaReunion) {
		
		String query = "SELECT * FROM salareuniones "
				+ " WHERE id = ? and num = ?";
		
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	        preparedStatement.setInt(1, salaReunion.getHotel().getID());
			preparedStatement.setInt(2, salaReunion.getNum());
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
	 * Modifica los datos de una sala de reuniones
	 * @param modificaciones
	 * @return
	 */
	public boolean update(SalaReunion modificaciones) {
				
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		SalaReunion original = new SalaReunion(0, 0, "", 0, null, "");
		
		// Copruebo que me han pasado el Primary Key correcto
		if (!((modificaciones.getNum() != 0) && (modificaciones.getHotel() == null))) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getHotel().getID(), modificaciones.getNum());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (modificaciones.getCapacidad() != 0) original.setCapacidad(modificaciones.getCapacidad());
			if (!modificaciones.getTlfno().equals("")) original.setTlfno(modificaciones.getTlfno());
			if (modificaciones.getPvp() != 0) original.setPvp(modificaciones.getPvp());
			if (!modificaciones.getServicios().equals("")) original.setServicios(modificaciones.getServicios());
		
		// En caso de no tener el DNI correcto devuelvo error
		} else {
			System.out.println("Error al insertar el Id de hotel y Numero de sala");
			return false;
		}
				
		// Revisa si existe el cliente
		if(check(original)) {
			
			String query = "UPDATE salareuniones "
					+ "SET id = ?, num = ?, servicios = ? "
					+ "WHERE id = ? and num = ? ;";
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
		        preparedStatement.setInt(1, original.getHotel().getID());
		        preparedStatement.setInt(2, original.getNum());
		        preparedStatement.setInt(3, original.getCapacidad());
		        preparedStatement.setString(4, original.getTlfno());
		        preparedStatement.setDouble(5, original.getPvp());
		        preparedStatement.setString(6, original.getServicios().toString());
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
		        return !check(original);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al actualizar el espacio comun");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	/**
	 * Recupera la informacion de una sala de reuniones
	 * @param idHotel
	 * @param num
	 * @return
	 */
	public SalaReunion get(int idHotel, int num) {

		String query = "SELECT * FROM salareuniones natural join sala "
				+ " WHERE id = ? and num = ?";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			pS.setInt(1, idHotel);
			pS.setInt(2, num);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				RepoHotel rH = new RepoHotel();
				SalaReunion e = new SalaReunion(
					rS.getInt(2),
					rS.getInt(4),
					rS.getString(5),
					rS.getDouble(6),
					rH.get(rS.getInt(1)),
					rS.getString(3)
				);
				return e;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Recupera una lista con salas de reunion
	 * @param filtro
	 * @return
	 */
	public ArrayList<SalaReunion> getListaFiltrada(SalaReunion filtro) {
		String query = 
				"SELECT  "
					+ "h.nom, "
					+ "s.num, "
					+ "s.capacidad, "
					+ "s.pvp, "
					+ "s.tlfno, "
					+ "r.servicios "
				+ "FROM "
					+ "Salareuniones r "
					+ "JOIN sala s USING(id, num) "
					+ "JOIN HOTEL H USING(ID) "
				+ "WHERE "
					+ "(h.nom = ? or ? = \"\") and "
					+ "(h.ciu = ? or ? = \"\") "
				+ "ORDER BY "
					+ "h.id ASC, "
					+ "s.num ASC;";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			
			if (filtro.getHotel() != null && !filtro.getHotel().getNombre().equals("")) {
				pS.setString(1, filtro.getHotel().getNombre());
				pS.setString(2, filtro.getHotel().getNombre());
			} else {
				pS.setString(1, "");
				pS.setString(2, "");
			}
				
			if (filtro.getHotel() != null && !filtro.getHotel().getCiudad().equals("")) {
				pS.setString(3, filtro.getHotel().getCiudad());
				pS.setString(4, filtro.getHotel().getCiudad());
			} else {
				pS.setString(3, "");
				pS.setString(4, "");
			}
			
			ResultSet rS = pS.executeQuery();
						
			ArrayList<SalaReunion> lista = new ArrayList<>();
			RepoHotel rH = new RepoHotel();
			while (rS.next()) {
				SalaReunion r = new SalaReunion(
						rS.getInt(2),
						rS.getInt(3),
						rS.getString(5),
						rS.getDouble(4),
						rH.get(rS.getInt(1)),
						rS.getString(6)
						);
				lista.add(r);
			}
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	/**
	 * Inserta una nueva sala de reuniones
	 * @param salaReunion
	 * @return
	 */
	public boolean insert(SalaReunion salaReunion) {
			
		// Revisa si ya existe el cliente
		if(!check(salaReunion)) {
			
			RepoSala rSa = new RepoSala();
			rSa.insert(salaReunion);
			
			String query = "INSERT INTO salaReuniones (id, num, servicios) "
					+ "VALUES "
					+ "(?, ?, ?)";
			
			//Si no existe el cliente, hace la consulta a la BBDD
	        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	            preparedStatement.setInt(1, salaReunion.getNum());
	            preparedStatement.setInt(2, salaReunion.getCapacidad());
	            preparedStatement.setString(3, salaReunion.getServicios());
	            preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        if (check(salaReunion)) {
		        	System.out.print("\n~~~ Sala de reuniones creada correctamente ~~~\n");
		        	return true;
		        } else {
		        	System.out.print("\n>>> Se ha producido un error <<<\n\n");
		        	return false;
		        }

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error al insertar la nueva sala de reuniones");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		System.out.println("La sala ya existe");
		return true;
	}
	
}
