package menu;

import conectores.*;
import model.*;
import model.Reserva;

/**
 * Menu para confirmar la reserva antes de meterla en el carro
 */
public class MenuReserva {

	/**
	 * Imprime el menu
	 * @param tipoDeHab
	 */
	public static void print(String tipoDeHab) {
		
		System.out.print(
				"\n\n~~~ Reserva ~~~\n"
				+ "------------------------------\n"
				);
		RepoHabitacion rH = new RepoHabitacion();
		Habitacion h = rH.getByTypeAndFirstDate(tipoDeHab);
		RepoReserva rR = new RepoReserva();
		Reserva r = new Reserva(
			rR.getNewID(), 
			MenuProductos.fecIni, 
			MenuProductos.fecFin, 
			Login.user, 
			h,
			Login.user.aplicarDcto(h.getPvp())
		);
		MenuCarrito.addCarrito(r);
		for(Reserva c : MenuCarrito.carrito) {
			System.out.print(
					".....................................\n" + 
					/* Get IdHotel				 		   NumSala				 Imprime
					   --- ------------------------------  --------------------  --------- */
					rH.get(c.getSala().getHotel().getID(), c.getSala().getNum()).toString() + 
					"\nDesde el " + c.getFecIni().toString() + " hasta el " + c.getFecFin().toString() +
					"\nPrecio con descuento aplicado: " + c.getPrecioTotal() + 
					"\n.....................................\n");
		}
		System.out.print(""
				+ "\nAñadida al carrito correctamente\n\n"
				+ "------------------------------\n");

		MenuPrincipal.print();
		
	}

}
