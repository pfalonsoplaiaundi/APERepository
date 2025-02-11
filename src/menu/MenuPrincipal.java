package menu;

import java.util.ArrayList;
import auxi.*;
import conectores.*;
import model.Hotel;

/**
 * Menu principal donde eliges el hotel y te mueves entre menus
 */
public class MenuPrincipal {
   
	/**
	 * Hotel seleccionado
	 */
	public static Hotel hotel;
	
	/**
	 * Impresion del menu
	 */
	public static void print() {
		
		System.out.print(
				"\n\n~~~ Menu principal ~~~\n" +
				((Login.user.isbTrabajador()) ? "-1. Para entrar en el menu de admin\n" : "") +
				"0. Para finalizar la compra o salir\n" +
				"------------------------------\n"
				);
		
		RepoHotel rH = new RepoHotel();
		ArrayList<String> hoteles = rH.getMenuPrincipal();
		int i = 0;
		while(i < hoteles.size()) {
			System.out.print((i+1) + ". " + hoteles.get(i) + "\n");
			i++;
		}
		
		System.out.print(
				"-----------------------------\n"
				+ "Cualquier otro numero para volver atras\n" +
				"Seleccione el hotel que desea: "
				);
		int opc = Input.inOpc();
		
		if (opc == 0) {
			System.out.print("¿Quieres deslogearte? ");
			if (Input.inYesNo()) {
				MenuCarrito.print();
			} else {
				print();
			}
		} else if (opc == -1 && Login.user.isbTrabajador()) {
			MenuAdmin.print();
		} else if (opc <= i && opc > 0) {
			int idhotel = rH.getPKByName(hoteles.get(opc-1));
			hotel = rH.get(idhotel);			
			MenuProductos.print(hotel.getID());
		} else {
			Login.main(null);
		}
	}
	
}
