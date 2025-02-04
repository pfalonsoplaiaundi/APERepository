package menu;

import auxi.Input;
import conectores.*;
import model.HabDisponible;
import model.Habitacion;
import model.Habitacion.tipoHab;
import model.Hotel;

public class MenuAdmin {
	
	public static void print() {
		System.out.print(
				"~~~ Menu de administración ~~~\n"
				+ "\n"
				+ "1. Reservas\n"
				+ "2. Clientes\n"
				+ "3. Hoteles\n"
				+ "4. Habitaciones\n"
				+ "5. Otros lugares\n"
				+ "0. Volver al menu principal\n");
		selector(Input.inOpc());
		
		
		
		
		System.out.print("Soy Admin");
		MenuPrincipal.print();
		
	}

	private static void selector(int opc) {
		switch (opc) {
		case 1:
			printReservas();
		case 2:
			printClientes();
		case 3:
			printHoteles();
		case 4:
			printHabitaciones();
		case 5:
			printOtros();
		case 0:
			MenuPrincipal.print();
		default:
			print();
		}
		
		
	}

	private static void printOtros() {
		System.out.print(
				"~~~ Menu de administración / Otros ~~~\n"
				+ "\n"
				+ "1. Salas de reunion\n"
				+ "2. Espacios comunes\n"
				+ "0. Volver atras\n");
		selectorOtros(Input.inOpc());
	}

	private static void selectorOtros(int opc) {
		switch (opc) {
		case 1:
			printReuniones();
		case 2:
			printComunes();
		case 0:
			print();
		default:
			printOtros();
		}
	}

	private static void printComunes() {
		// TODO Auto-generated method stub
		
	}

	private static void printReuniones() {
		// TODO Auto-generated method stub
		
	}

	private static void printHabitaciones() {
		System.out.print(
				"~~~ Menu de administración / Habitaciones ~~~\n"
				+ "\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n");
		selectorHabitaciones(Input.inOpc());
		
	}

	private static void filtroHabitaciones() {
		System.out.print("\n¿Deseas filtrar el resultado? (Si/no): ");
		boolean respuesta = Input.inYesNo();
		if (respuesta) {

			
			int opc = 0;
			int disponible = -1; // -1 - vacio, 0 - no diponible, 1 - diponible 
			Habitacion filtro = new Habitacion(null, 0, 0, "", 0, "");
			do {
	
				// Print filtro
				printMenuFiltroHabitaciones(filtro, disponible);
				opc = Input.inOpc();
				
				// Selector
				switch (opc) {
				case 1:
					RepoHotel rH = new RepoHotel();
					filtro.setHotel(rH.get(rH.getPKByName(Input.inNombre())));
				
				case 2:
					filtro.setTipo(filtroTipoHab());
					
				case 3:
					System.out.print(
							"¿Quieres que esten libre u ocupadas?"
							+ "1. Libres\n"
							+ "2. Ocupadas\n"
							);
					opc = Input.inOpc();
					switch (opc) {
					case 1:
						disponible = 1;
					case 2:
						disponible = 0;
					default:
						disponible = -1;
					}
					
				case 4:
					filtro.setPvp(Input.inPvp());
					
				default:
					break;
					
				}
			} while(opc != 0);
			
						
			// Print resultado
			printResultadoFiltroHabitacion(filtro, disponible);
				
		}
	}
	
	/**
	 * Imprime el menu de filtro, rellenando los valores ya seteados y dejando solo las opciones que esten libres.
	 * @param filtro
	 * @param disponible
	 */
	private static void printMenuFiltroHabitaciones(Habitacion filtro, int disponible) {
		System.out.print(
				"\n"
				+ ((filtro.getHotel() == null) ? "1. Por hotel\n" : filtro.getHotel().getNombre())
				+ ((filtro.getTipo() == tipoHab.desconocido) ? "2. Por tipo\n" : filtro.getTipo().toString())
				+ ((disponible == -1) ? "3. Por disponibilidad\n" : ((disponible == 1) ? "Habitaciones libres" : "Habitaciones ocupadas"))
				+ ((filtro.getPvp() == 0) ? "4. Por precio\n" : ("Al menos " + filtro.getPvp() + "€"))
				+ "0. Para continuar\n"
		);
	}

	private static void printResultadoFiltroHabitacion(Habitacion filtro, int disponible) {
		RepoHabitacion rH = new RepoHabitacion();
		System.out.print( "Hotel\t|\tHabitacion\t|\tTipo\t|\tTelefono\t|\tPVP\t|\tOcupada" );
		for(HabDisponible h : rH.getListaFiltrada(filtro, disponible)) {
			int i = 0;
			System.out.print(
					h.getHabitacion().getHotel().getNombre() + "\t|\t" + 
					h.getHabitacion().getNum() + "\t|\t" +
					h.getHabitacion().getTipo().toString() + "\t|\t" +
					h.getHabitacion().getTlfno() + "\t|\t" +
					h.getHabitacion().getPvp() + "€\t|\t" +
					h.isDisponible() + "\n"
				);
			i++;
			if (i%5 == 0) System.out.print("-----------------------------------------------------------------------\n");
		};
	}

	private static tipoHab filtroTipoHab() {
		int opc = 0;
		do {
			
			// Menu de seleccion
			System.out.print(
					"¿Que tipo de habitacion deseas?\n"
					+ "1. Individual\n"
					+ "2. Doble\n"
					+ "3. Familiar\n"
					+ "4. Suite\n"
					+ "5. Apartamento\n"
					+ "Selecciona el tipo: "
			);
			opc = Input.inOpc();
			
			// Actua segun la opcion y devuelve el enum correspondiente
			switch (opc) {
			case 1:
				return tipoHab.individual;  
			case 2:
				return tipoHab.doble;
			case 3:
				return tipoHab.familiar;
			case 4:
				return tipoHab.suite;
			case 5:
				return tipoHab.apartamento;
			default:
				System.out.print("ERROR, elige una de las opciones disponibles");
				opc = 0;
			}
		} while (opc == 0);
		return null;
	}
	
	/**
	 * Selector del menu de administracion - apartado habitaciones
	 * @param opc
	 */
	private static void selectorHabitaciones(int opc) {		
		switch (opc) {
		case 1:
			filtroHabitaciones();
			
			
		case 2:
			printComunes();
		case 0:
			print();
		default:
			printOtros();
		}
	}

	private static void printHoteles() {
		System.out.print(
				"~~~ Menu de administración / Hoteles ~~~\n"
				+ "\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n");
		selectorHoteles(Input.inOpc());
	}

	private static void selectorHoteles(int inOpc) {
		// TODO Auto-generated method stub
		
	}

	private static void printClientes() {
		System.out.print(
				"~~~ Menu de administración / Clientes ~~~\n"
				+ "\n"
				+ "1. Listado\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n");
		selectorClientes(Input.inOpc());
	}

	private static void selectorClientes(int inOpc) {
		// TODO Auto-generated method stub
		
	}

	private static void printReservas() {
		System.out.print(
				"~~~ Menu de administración / Reservas ~~~\n"
				+ "\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n");
		selectorReservas(Input.inOpc());
	}

	private static void selectorReservas(int inOpc) {
		// TODO Auto-generated method stub
		
	}

}
