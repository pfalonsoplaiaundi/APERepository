package conectores;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.EspacioComun;

/**
 * Conexion a la base de datos que hace las peticiones de espacio comun
 */
public class RepoEspacioComun {

	public ArrayList<EspacioComun> getListaFiltrada(EspacioComun filtro) {
		String query = 
				"SELECT  "
					+ "h.id, "
					+ "s.num, "
					+ "s.capacidad, "
					+ "s.pvp, "
					+ "s.tlfno, "
					+ "e.tipo "
				+ "FROM "
					+ "EspaciosComunes e "
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
						
			ArrayList<EspacioComun> lista = new ArrayList<>();
			RepoHotel rH = new RepoHotel();
			while (rS.next()) {
				EspacioComun e = new EspacioComun(
						rS.getInt(2),
						rS.getInt(3),
						rS.getString(5),
						rS.getDouble(4),
						rH.get(rS.getInt(1)),
						rS.getString(6)
						);
				lista.add(e);
			}
			
			return lista;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	/**
	 * Esta funcion inserta un espacio comun nuevo.
	 * @param nuevo
	 * @return
	 */
	public boolean insert(EspacioComun nuevo) {
			
		// Revisa si ya existe el cliente
		if(!check(nuevo)) {
			
			RepoSala rSa = new RepoSala();
			rSa.insert(nuevo);
			
			String query = "INSERT INTO espaciosComunes (id, num, tipo) "
					+ "VALUES "
					+ "(?, ?, ?);";
			
			//Si no existe el cliente, hace la consulta a la BBDD
	        try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	            preparedStatement.setInt(1, nuevo.getHotel().getID());
	            preparedStatement.setInt(2, nuevo.getNum());
	            preparedStatement.setString(3, nuevo.getTipo());
	            
	            preparedStatement.executeUpdate();
		        
		        //Comprueba si la insercion se ha producido y devuelve en funcion de esta
		        if (check(nuevo)) {
		        	System.out.print("\n~~~ Espacio comun creada correctamente ~~~\n");
		        	return true;
		        } else {
		        	System.out.print("\n>>> Se ha producido un error <<<\n\n");
		        	return false;
		        }

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error al insertar el nuevo espacio comun");
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		System.out.println("La sala ya existe");
		return true;
	}

	/**
	 * Comprueba que una sala exista en la base de datos
	 * @param espacioComun
	 * @return
	 */
	public boolean check(EspacioComun espacioComun) {
		
		String query = "SELECT * FROM espacioscomunes "
				+ " WHERE id = ? and num = ?";
		
		try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
	        preparedStatement.setInt(1, espacioComun.getHotel().getID());
			preparedStatement.setInt(2, espacioComun.getNum());
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
	 * Esta funcion modifica un espacio comun
	 */
	public boolean update(EspacioComun modificaciones) {
				
		//Inicializo un cliente que va a recibir los datos del cliente original, lo hago fuera del if para poder usarlo despues.
		EspacioComun original = new EspacioComun(0, 0, "", 0, null, "");
		
		// Copruebo que me han pasado el Primary Key correcto
		if (!((modificaciones.getNum() != 0) && (modificaciones.getHotel() == null))) {
			
			// Meto los datos del cliente original en el cliente creado anterior
			original = get(modificaciones.getHotel().getID(), modificaciones.getNum());
			
			// Reviso si un dato esta por defecto y en caso de que no lo este en modificaciones lo tomo como una modificacion del original y lo seteo.
			if (modificaciones.getCapacidad() != 0) original.setCapacidad(modificaciones.getCapacidad());
			if (!modificaciones.getTlfno().equals("")) original.setTlfno(modificaciones.getTlfno());
			if (modificaciones.getPvp() != 0) original.setPvp(modificaciones.getPvp());
			if (!modificaciones.getTipo().equals("")) original.setTipo(modificaciones.getTipo());
		
		// En caso de no tener el DNI correcto devuelvo error
		} else {
			System.out.println("Error al insertar el Id de hotel y Numero de sala");
			return false;
		}
				
		// Revisa si existe el cliente
		if(check(original)) {
			
			RepoSala rSa = new RepoSala();
			rSa.update(modificaciones);
			
			String query = "UPDATE espacioscomunes "
					+ "SET tipo = ? "
					+ "WHERE id = ? and num = ? ;";
			
			//Si existe el cliente, ejecuta el borrado en la BBDD
			try (PreparedStatement preparedStatement = ConectMySQL.conexion.prepareStatement(query)) {
		        preparedStatement.setString(1, original.getTipo());
		        preparedStatement.setInt(2, original.getHotel().getID());
		        preparedStatement.setInt(3, original.getNum());
		        System.out.println(preparedStatement);
		        
		        preparedStatement.executeUpdate();
		        
		        //Comprueba si la modificacion se ha producido y devuelve lo contrario en funcion de esta
		        return !check(original);

			//En caso de que haya algun error en la base lo coge aqui
			} catch (SQLException e) {
				System.out.println("Error al actualizar el espacio comun" +e);
				return false;
			}
		}
		
		//Si el cliente existe antes de la insercion devuelve false. 
		return false;
	}

	/**
	 * Recupera de la BBDD informacion de un espacio comun
	 * @param idHotel
	 * @param num
	 * @return
	 */
	public EspacioComun get(int idHotel, int num) {

		String query = "SELECT * FROM espacioscomunes natural join sala "
				+ " WHERE id = ? and num = ?";
		
		try (PreparedStatement pS = ConectMySQL.conexion.prepareStatement(query)) {
			pS.setInt(1, idHotel);
			pS.setInt(2, num);
			ResultSet rS = pS.executeQuery();
			if (rS.next()) {
				RepoHotel rH = new RepoHotel();
				EspacioComun e = new EspacioComun(
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
	 * Esta funcion borra un espacio comun
	 * @param aBorrar
	 * @return
	 */
	public boolean delete(EspacioComun aBorrar) {
	
		// Revisa si existe el cliente
		if(check(aBorrar)) {

			String query = "DELETE FROM sala "
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

}
