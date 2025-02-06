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
		Habitacion h = rH.getByTypeAndFirstDate(tipoDeHab);
		Reserva r = new Reserva(
			rR.getNewID(), 
			Input.inFecIni(), 
			Input.inFecFin(), 
			Login.user, 
			h,
			Login.user.aplicarDcto(h.getPvp())
		);
		
		MenuCarrito.addCarrito(r);
		MenuPrincipal.print();
		
	}

}
