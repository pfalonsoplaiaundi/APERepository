package menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import auxi.Input;
import conectores.RepoHotel;
import conectores.RepoSala;
import model.HabReserva;

public class MenuProductos {

	public static void print(int idHotel) {
		RepoSala rS = new RepoSala();
		System.out.print(
				"\n\n~~~ Menu Productos ~~~\n"
				+ "------------------------------\n"
				+ "Tipo de Habitacion\t|\tFecha más proxima disponible\n"
				+ "---------------------------------------------------------------\n"
				);
		ArrayList<HabReserva> salas = rS.getMenuProductos(idHotel);
		int i = 1;
		for(HabReserva s : salas) {
			System.out.print(
					i + ". " + s.getTipoHab().toString() + "\t\t|\t" + s.getFecha() + "\n"
					);
			i++;
		}
		System.out.print("0. otros\n"
				+ "-----------------------------\n"
				+ "Seleccione la habitacion que desea: ");
		int opc = Input.inOpc();
		
		MenuReserva.print(salas.get(opc-1).getTipoHab().toString());
		
	}

}
