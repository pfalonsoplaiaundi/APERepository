package menu;

import java.util.ArrayList;

import auxi.Input;
import conectores.RepoHabitacion;
import conectores.RepoReserva;
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
				System.out.print(i + ". " + rH.get(c.getSala().getHotel().getID(), c.getSala().getNum()).toString());
			}
			System.out.print(
					Login.user.toString()
					+"\n¿Es correcto? ");
			boolean respuesta = Input.inYesNo();
			if (respuesta) {
				pasarelaPago();

				carrito.clear();
				MenuCarrito.print();
			} else {
				MenuPrincipal.print();
			}
			
		} else {
			System.out.print("\n\n\n\n\n\n\n\n>>> Fin de programa <<<");
		}
	}

	private static void pasarelaPago() {
		System.out.print("\n\n~~~ Pasarela de pago ~~~\n"
				+ "¿Que metodo de pago va a usar?\n"
				+ "1. Tarjeta de credito\n"
				+ "2. Transferencia bancaria\n"
				+ "3. PayPal\n"
				+ "4. Bizum\n"
				+ "5. In situ\n"
				+ "Seleccione el metodo que desee: "
				);
		selectorPasarelaPago(Input.inOpc());
		
	}

	private static void selectorPasarelaPago(int opc) {
		RepoReserva rR = new RepoReserva();
		switch (opc) {
		case 1:
			Input.inTarjeta();
			System.out.print(
					"Estamos procesando el pago...\n\n\n" +
					"Muchas gracias por su compra, esperamos que vuelva a contar con nosotros"
					);
			for(Reserva c : carrito) {
				c.setbPagada(true);
				rR.insert(c);
			}
			break;
		case 2:
			System.out.print(
					"Nuestra cuenta corriente es: ES46 6353 1454 6132 1546\n" +
					"Proceda al pago con su DNI y cuando recibamos el pago confirmaremos su reserva.\n\n" +
					"Muchas gracias por su compra, esperamos que vuelva a contar con nosotros"
					);
			for(Reserva c : carrito) {
				rR.insert(c);
			}
			break;
		case 3:
			System.out.print(
					"Le estamos redirigiendo...\n\n\n" +
					"Muchas gracias por su compra, esperamos que vuelva a contar con nosotros"
					);
			for(Reserva c : carrito) {
				c.setbPagada(true);
				rR.insert(c);
			}
			break;
		case 4:
			System.out.print(
					"Le enviaremos una peticion a su numero de telefono en breves...\n" +
					"Proceda al pago con su DNI y cuando recibamos el pago confirmaremos su reserva.\n\n" +
					"Muchas gracias por su compra, esperamos que vuelva a contar con nosotros"
					);
			for(Reserva c : carrito) {
				rR.insert(c);
			}
			break;
		case 5:
			System.out.print(
					"Muchas gracias por su compra, esperamos que vuelva a contar con nosotros"
					);
			for(Reserva c : carrito) {
				rR.insert(c);
			}
			break;
		default:
			pasarelaPago();
		}
		
	}

	public static void addCarrito(Reserva r) {
		carrito.add(r);
		
	}

}
