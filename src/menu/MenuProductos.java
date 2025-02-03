package menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import auxi.Input;
import conectores.RepoHotel;
import conectores.RepoSala;
import model.HabReserva;
import model.Hotel;

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
				+ "Cualquier otro numero para volver atras\n"
				+ "Seleccione la habitacion que desea: ");
		int opc = Input.inOpc();
		RepoHotel rH = new RepoHotel();
		if (opc == 0) {
			System.out.print("\n\nPor favor contacte con nuestro hotel en el: " + rH.get(idHotel).getTlfno() + "\n"
				+ "Nuestros trabajadores le atenderan con su peticion\n\n");
			MenuPrincipal.print();
		} else if (opc <= i) {	
			MenuReserva.print(salas.get(opc-1).getTipoHab().toString());
		} else {
			MenuPrincipal.print();
		}
	}

}
