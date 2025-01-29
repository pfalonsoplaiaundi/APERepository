package menu;

import auxi.Input;
import conectores.*;
import model.*;
import model.Reserva;

public class MenuReserva {

	public void print(String tipoDeHab) {
		
		System.out.print(
				"\n\n~~~ Reserva ~~~\n"
				+ "------------------------------\n"
				);
		RepoCliente rC = new RepoCliente();
		RepoReserva rR = new RepoReserva();
		RepoSala rS = new RepoSala();
		RepoHabitacion rH = new RepoHabitacion();
		Cliente c = rC.get(Login.user);
		Reserva r = new Reserva(rR.getNewID(), Input.inFecIni(), Input.inFecFin(), rC.get(Login.user), rS.getByTypeAndFirstDate(tipoDeHab));
		
	}

}
