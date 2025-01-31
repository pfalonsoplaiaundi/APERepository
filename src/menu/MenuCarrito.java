package menu;

import java.util.ArrayList;

import conectores.RepoHabitacion;
import model.Reserva;

public class MenuCarrito {

	public static ArrayList<Reserva> carrito = new ArrayList<>();
	
	public static void print() {
		if (!carrito.isEmpty()) {
			System.out.print("\n~~~ Carrito ~~~\n");
			RepoHabitacion rH = new RepoHabitacion();
			int i = 0;
			for(Reserva c : carrito) {
				i++;
				/* Obtengo una habitacion con la reserva del carrito, y la imprimo
				* 						    Get	   IdHotel				 NumSala						 Imprime
				*                           ------ --------------------  ------------------------------  --------- */
				System.out.print(i + ". " + rH.get(c.getSala().getNum(), c.getSala().getHotel().getID()).toString());
			}
			
		} else {
			System.out.print("\n\n\n\n\n\n\n\n>>> Fin de programa <<<");
		}
	}

	public static void addCarrito(Reserva r) {
		carrito.add(r);
		
	}

}
