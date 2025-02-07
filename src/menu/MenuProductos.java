package menu;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import auxi.Input;
import conectores.RepoHotel;
import conectores.RepoSala;
import model.HabReserva;
import model.Habitacion;
import model.Hotel;

public class MenuProductos {

	public static Date fecIni;
	public static Date fecFin;
	
	public static void print(int idHotel) {
		RepoSala rS = new RepoSala();
		fecIni = Input.inFecIni();
		fecFin = Input.inFecFin();
		System.out.print(
				"\n\n~~~ Menu Productos ~~~\n"
				+ "------------------------------\n"
				+ "Tipo de Habitacion\t| Precio\n"
				+ "---------------------------------------------------------------\n"
				);
		ArrayList<Habitacion> habitaciones = rS.getMenuProductos();
		int i = 1;
		for(Habitacion h : habitaciones) {
			System.out.print(
					i + ". " + h.getTipo().toString() + "\t\t| " + h.getPvp() + "€\n"
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
			MenuReserva.print(habitaciones.get(opc-1).getTipo().toString());
		} else {
			MenuPrincipal.print();
		}
	}

}
