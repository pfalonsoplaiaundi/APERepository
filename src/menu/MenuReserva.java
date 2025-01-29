package menu;

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
		Cliente c = rC.get(Login.user);
		Reserva r = new Reserva(rR.getNewID(), rC.get(Login.user))
		
	}

}
