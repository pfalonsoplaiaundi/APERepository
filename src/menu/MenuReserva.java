package menu;

import auxi.Input;
import conectores.*;
import model.*;
import model.Reserva;

public class MenuReserva {

	public static void print(String tipoDeHab) {
		
		System.out.print(
				"\n\n~~~ Reserva ~~~\n"
				+ "------------------------------\n"
				);
		RepoReserva rR = new RepoReserva();
		RepoSala rS = new RepoSala();
		RepoHabitacion rH = new RepoHabitacion();
		Reserva r = new Reserva(
			rR.getNewID(), 
			Input.inFecIni(), 
			Input.inFecFin(), 
			Login.user, 
			rH.getByTypeAndFirstDate(tipoDeHab)
		);
			
		
		
		MenuCarrito.addCarrito(r);
		MenuPrincipal.print();
		
	}

}
