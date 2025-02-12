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
			double subtotal = 0;
			for(Reserva c : carrito) {
				// Obtengo una habitacion con la reserva del carrito, y la imprimo
				System.out.print(
						".....................................\n" + 
						/* Get IdHotel				 		   NumSala				 Imprime
						   --- ------------------------------  --------------------  --------- */
						rH.get(c.getSala().getHotel().getID(), c.getSala().getNum()).toString() + 
						"\nDesde el " + c.getFecIni().toString() + " hasta el " + c.getFecFin().toString() +
						"\nPrecio con descuento aplicado: " + c.getPrecioTotal() + 
						"\n.....................................\n");
				subtotal += c.getPrecioTotal();
			}
			System.out.print(
					Login.user.toString()
					+"\n\nSUBTOTAL: " + subtotal
					+"\n¿Es correcto? (no para volver atras) ");
			boolean respuesta = Input.inYesNo();
			if (respuesta) {
				pasarelaPago();
				carrito.clear();
				MenuCarrito.print();
			} else {
				modificarCarrito();
				MenuPrincipal.print();
			}
			
		} else {
			System.out.print("\n\n\n\n\n\n\n\n>>> Fin de programa <<<\n\n\n\n\n\n\n");
			System.exit(0);
		}
	}

	private static void modificarCarrito() {
		System.out.print("¿Deseas modificar el carrito? (no para volver atras) ");
		boolean respuesta = Input.inYesNo();
		if (respuesta) {
			System.out.print("\n~~~ Reservas ~~~\n");
			for (Reserva r : carrito) {
				System.out.print(	
									"...............\n " + 
									r.toString() + 
									"\n..............\n"
								);
			}
			System.out.print("Elige el id de la reserva que deseas modificar: ");
			int id = Input.inOpc();
			printModificarReserva(id);
			
		} 	
	}
	
	private static void printModificarReserva(int id) {
		System.out.print("\n----- Modificar reserva -----\n"
				+ "1. Eliminar\n"
				+ "2. Modificar fechas\n"
				+ "Seleccione lo que desea hacer: ");
		selectorModificarReserva(Input.inOpc(), id);
	}
	
	private static void selectorModificarReserva(int opc, int id) {
		switch (opc) {
		case 1:
			int i = 0;
			boolean bEncontrado = false;
			while (i < carrito.size() && !bEncontrado) {
				if (id != carrito.get(i).getID()) {
					i++;
				} else {
					bEncontrado = true;
					carrito.remove(i);
					if (carrito.isEmpty()) {
						MenuPrincipal.print();
					} else {
						print();
					}
				}
			}
			System.out.print("Error al introducir el id de reserva\n");
			modificarCarrito();

		case 2:
			System.out.print("\n\n >>> Elimina la reserva y vuelve a crear la reserva <<<\n\n");
			modificarCarrito();
		default:
			System.out.print("\n\n>>> ERROR: Elige una opcion valida <<<\n\n");
			printModificarReserva(id);
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
		System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\nMuchas gracias por su compra, \nesperamos que vuelva a contar con nosotros\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
	}

	private static void selectorPasarelaPago(int opc) {
		RepoReserva rR = new RepoReserva();
		System.out.print("\n----------------------------------------------------------------\n");
		switch (opc) {
		case 1:
			Input.inTarjeta();
			System.out.print(
					"Estamos procesando el pago...\n\n\n"
					);
			for(Reserva c : carrito) {
				c.setbPagada(true);
				rR.insert(c);
			}
			break;
		case 2:
			System.out.print(
					"Nuestra cuenta corriente es: ES46 6353 1454 6132 1546\n" +
					"Proceda al pago con su DNI y cuando recibamos el pago confirmaremos su reserva.\n\n"
					);
			for(Reserva c : carrito) {
				rR.insert(c);
			}
			break;
		case 3:
			System.out.print(
					"Le estamos redirigiendo...\n\n\n"
					);
			for(Reserva c : carrito) {
				c.setbPagada(true);
				rR.insert(c);
			}
			break;
		case 4:
			System.out.print(
					"Le enviaremos una peticion a su numero de telefono en breves...\n" +
					"Proceda al pago con su DNI y cuando recibamos el pago confirmaremos su reserva.\n\n"
					);
			for(Reserva c : carrito) {
				rR.insert(c);
			}
			break;
		case 5:
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
