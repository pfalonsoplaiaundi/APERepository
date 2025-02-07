package menu;

import java.util.ArrayList;

import auxi.Input;
import conectores.*;
import model.HabDisponible;
import model.Habitacion;
import model.Habitacion.tipoHab;
import model.Hotel;
import model.Reserva;
import model.*;

public class MenuAdmin {
	
	/**
	 * Imprime el menu inicial de administracion.
	 */
	public static void print() {
		System.out.print(
				"\n\n~~~ Menu de administración ~~~\n"
				+ "1. Reservas\n"
				+ "2. Clientes\n"
				+ "3. Hoteles\n"
				+ "4. Habitaciones\n"
				+ "5. Otros lugares\n"
				+ "0. Volver al menu principal\n"
				+ "Seleccione la opcion que desea: ");
		selector(Input.inOpc());		
	}

	/**
	 * Selector del menu de administracion, todas las opciones llevan a otros menus.
	 * @param opc
	 */
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
	
	/**
	 * Imprime el submenu de administracion de reservas
	 */
	private static void printReservas() {
		System.out.print(
				"\n\n~~~ Menu de administración / Reservas ~~~\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n"
				+ "Seleccione la opcion que desea: ");
		selectorReservas(Input.inOpc());
	}
	
	/**
	 * WIP Logica del menu de administracion / reservas
	 * @param opc
	 */
	private static void selectorReservas(int opc) {
		switch (opc) {
		case 1:
			listaReserva();
			break;
		case 2:
			addReserva();
			break;
		case 3:
			modifyReserva();
			break;
		case 4:
			//delteReserva();
			break;
		case 0:
			print();
		default:
			printReservas();
		}
		printReservas();
	}
	
	private static void modifyReserva() {
		System.out.print( 
				"¿Que reserva deseas modificar?\n"
				+ "1. Buscar por CODIGO\n"
				+ "2. Buscar por CLIENTE\n"
				+ "3. Buscar por HOTEL\n"
				+ "4. Buscar por FECHAS\n"
				+ "Seleccione la opcion que deseas: "
				);
		Reserva aModificar = selectorModifyReservas(Input.inOpc());
		System.out.print(
				"\n~~~ Datos actuales ~~~\n" +
				aModificar.toString() + "\n" +
				"\n¿Que desea modificar?" +
				"1. Cliente\n" +
				"2. Fecha de inicio\n" +
				"3. Fecha de finalizacion\n" +
				"Selecciones la opcion que desee: "
				) ;
		
	}

	/**
	 * 
	 * @param opc
	 */
	private static Reserva selectorModifyReservas(int opc) {
		Reserva aModificar = null;
		RepoReserva rR = new RepoReserva();
		Reserva filtro = null;
		switch (opc) {
		
		
		case 1:
			aModificar = rR.get(Input.inCod());
			break;
			
			
		case 2:
			RepoCliente rC = new RepoCliente();
			filtro = new Reserva(0, null, null, rC.get(Input.inDNI()), null, 0);
			printReservasCliente(filtro);
			System.out.print("Seleccione la reserva que desea: ");
			aModificar = rR.get(Input.inCod());
			break;
		
		
		case 3:
			RepoHotel rH = new RepoHotel();
			Sala s = new Sala(0, 0, "", 0, rH.get(rH.getPKByName(Input.inNombre())), "");
			filtro = new Reserva(0, null, null, null, s, 0);
			printReservasCliente(filtro);
			System.out.print("Seleccione la reserva que desea: ");
			aModificar = rR.get(Input.inCod());
			break;
		
		
		case 4:
			System.out.print("\n"
					+ "1. Por fecha de inicio\n"
					+ "2. Por fecha de finalizacion\n"
					+ "3. Por ambas fechas\n"
					+ "0. Volver atras\n"
					+ "Seleccione la opcion que desee: ");
			int opc2 = Input.inOpc();
			
			switch (opc2) {
			case 1:
				filtro.setFecIni(Input.inFecIni());
				break;
			case 2:
				filtro.setFecFin(Input.inFecFin());
				break;
			case 3:
				filtro.setFecIni(Input.inFecIni());
				filtro.setFecFin(Input.inFecFin());
				break;
			default:
				break; 
			}
			
			printReservasCliente(filtro);
			System.out.print("Seleccione la reserva que desea: ");
			aModificar = rR.get(Input.inCod());
			break;
		
		
		default:
			modifyReserva();
		}
		return aModificar;
	}

	/**
	 * 
	 * @param filtro
	 */
	private static void printReservasCliente(Reserva filtro) {
		RepoReserva rR = new RepoReserva();
		ArrayList<Reserva> lista = rR.getListaFiltrada(filtro);
		if (!(lista == null)) {
		
			System.out.print(
					"Id" + "\t| " 
					+ "Fecha Inicio" + "\t| "
					+ "Fecha Fin" + "\t| "
					+ "DNI" + "\t\t| "
					+ "Nombre" + "\t| "
					+ "Apellido/s" + "\t| "
					+ "Hotel" + "\t\t| "
					+ "Numero habitacion" + "\n"
					+ "--------------------------------------------------------------------------------------------------------------------\n");
			int i = 0;
			while (i < lista.size()) {
				System.out.print(
						lista.get(i).getID() + "\t| "
						+ lista.get(i).getFecIni().toString() + "\t| "
						+ lista.get(i).getFecFin().toString() + "\t| "
						+ lista.get(i).getCliente().getDNI() + "\t| "
						+ (
							(lista.get(i).getCliente().getNombre().length() < 6) ? 
								(lista.get(i).getCliente().getNombre() + "\t\t| ") : 
								(lista.get(i).getCliente().getNombre() + "\t| ")
						)
						+ (
								(lista.get(i).getCliente().getApellidos().length() < 6) ? 
									(lista.get(i).getCliente().getApellidos() + "\t\t| ") : 
									(lista.get(i).getCliente().getApellidos() + "\t| ")
							)
						+ lista.get(i).getSala().getHotel().getNombre() + "\t| "
						+ lista.get(i).getSala().getNum() + "\n"
				);
				i++;
				if (i%5 == 0) System.out.print("--------------------------------------------------------------------------------------------------------------------\n");
			}
			System.out.print("\n");
			
		} else {
			System.out.print("\n\n>>> No hay reservas <<<\n\n");
		}
	}
	
	/**
	 * Funcion para crear una reserva desde el menu de administracion
	 */
	private static void addReserva() {
		RepoReserva rR = new RepoReserva();
		RepoCliente rC = new RepoCliente();
		RepoSala rS = new RepoSala();
		RepoHotel rH = new RepoHotel();
		ArrayList<String> hoteles = rH.getMenuPrincipal();
		int i = 0;
		while(i < hoteles.size()) {
			System.out.print((i+1) + ". " + hoteles.get(i) + "\n");
			i++;
		}	
		System.out.print("Seleccione el hotel que desea: ");
		int hotel = Input.inOpc();
	
		Hotel h = rH.get(hotel);
		System.out.print("\n>>> Recuerda tener todos los datos (incluido el numero de habitacion) <<<\n");
		Reserva r = new Reserva(
							rR.getNewID(),
							Input.inFecIni(),
							Input.inFecFin(),
							rC.get(Input.inDNI()),
							rS.get(h.getID(), Input.inNum()),
							0
						);
		rR.insert(r);
		System.out.println("");
		
	}

	/**
	 * Funcion para desplegar la lista de reservas completas 
	 * en caso de no querer filtrar
	 */
	private static void listaReserva() {
		if (!filtroReserva()) {
			Reserva filtro = new Reserva(0, null, null, null, null, 0);
			printResultadoFiltroReserva(filtro);
		}	
	}
	
	/**
	 * Funcion que te permite filtrar la lista resultados de reservas
	 * Devuelve true, si se ha filtrado, false si no.
	 * @return
	 */
	private static boolean filtroReserva() {
		System.out.print("\n¿Deseas filtrar el resultado? ");
		boolean respuesta = Input.inYesNo();
		if (respuesta) {

			int opc = 0;
			Reserva filtro = new Reserva(0, null, null, null, null, 0);
			do {
	
				// Print filtro
				printMenuFiltroReserva(filtro);
				opc = Input.inOpc();
				
				// Selector
				filtro = selectorMenuFiltroReserva(filtro, opc);
			} while(opc != 0);
			
						
			// Print resultado
			printResultadoFiltroReserva(filtro);
			return true;
		}
		return false;
	}

	/**
	 * Imprime el menu de filtro, rellenando los valores ya 
	 * seteados y dejando solo las opciones que esten libres.
	 * @param filtro
	 * @param disponible
	 */
	private static void printMenuFiltroReserva(Reserva filtro) {
		System.out.print(
				"\n>>> Filtro <<<\n"
				
				// 1. Por hotel		
				+ 														 
				((filtro.getSala() == null) ?
						"1. Por hotel\n" :
						((filtro.getSala().getHotel() == null) ? 
							"1. Por hotel\n" : 						
							filtro.getSala().getHotel().getNombre() + "\n"
						)
				)
				
				// 2. Por cliente
				+ 
				((filtro.getCliente() == null) ?
						"2. Por cliente\n" :
						((filtro.getCliente().getDNI() == "") ? 
								"2. Por cliente\n" : 
								filtro.getCliente().getNombre() + " " + filtro.getCliente().getNombre() + "\n"
						)
				)
				
				// 3. Por numero de habitacion
				+ 
				((filtro.getSala() == null) ?
						"3. Por numero de habitacion\n" :
						((filtro.getSala().getNum() == 0) ? 
								"3. Por numero de habitacion\n" : 
								filtro.getSala().getNum() + "\n"
						)
				)
				
				// 4. Por fecha
				+ // Si no esta el filtro muestra "4. Por fechas" si esta relleno, solo con minimo o maximo o ambos muestra las fechas.
				(((filtro.getFecIni() == null) && (filtro.getFecFin() == null)) ? 
						"4. Por fechas\n" : 
						(((filtro.getFecIni() != null) && (filtro.getFecFin() == null)) ?
								"Desde " + filtro.getFecIni().toString() + "\n":
								(((filtro.getFecIni() == null) && (filtro.getFecFin() != null)) ?
										"Hasta " + filtro.getFecFin().toString() + "\n":
										"Desde " + filtro.getFecIni().toString() + " hasta " + filtro.getFecFin().toString() + "\n"
								)
						)
				)
				
				+ "0. Para continuar\n"
		);
	}
	
	private static Reserva selectorMenuFiltroReserva(Reserva filtro, int opc) {
		switch (opc) {
		
		// Filtro hotel
		//--------------------------------------------------------------------------------------------------------------
		case 1: // Si no hay una sala asociada la crea con el hotel, si ya existe le asigna el hotel
			RepoHotel rH = new RepoHotel();
			ArrayList<String> hoteles = rH.getMenuPrincipal();
			int i = 0;
			while(i < hoteles.size()) {
				System.out.print((i+1) + ". " + hoteles.get(i) + "\n");
				i++;
			}	
			System.out.print("Seleccione el hotel que desea: ");
			int hotel = Input.inOpc();
		
			if (filtro.getSala() == null) {
				Sala s = new Sala(0, 0, "", 0, rH.get(hotel), "");
				filtro.setSala(s);
			} else {
				filtro.getSala().setHotel(rH.get(hotel));
			}
			break;
			
		// Filtro cliente
		//--------------------------------------------------------------------------------------------------------------		
		case 2: // Añade y sobreescribe el cliente al filtro
			RepoCliente rC = new RepoCliente();
			filtro.setCliente(rC.get(Input.inDNI()));
			break;
			
		// Filtro sala	
		//--------------------------------------------------------------------------------------------------------------
		case 3: // Si no hay una sala en el filtro la crea con el numero, si ya existe sobreescribe el numero
			if (filtro.getSala() == null) {
				Sala s = new Sala(Input.inNum(), 0, "", 0, null, "");
				filtro.setSala(s);
			} else {
				filtro.getSala().setNum(Input.inNum());
			}
			break;
			
		// Filtro fecha	
		//--------------------------------------------------------------------------------------------------------------
		case 4: // Por fecha
			System.out.print("\n"
					+ "1. Por fecha de inicio\n"
					+ "2. Por fecha de finalizacion\n"
					+ "3. Por ambas fechas\n"
					+ "0. Volver atras\n"
					+ "Seleccione la opcion que desee: ");
			int opc2 = Input.inOpc();
			
			switch (opc2) {
			case 1:
				filtro.setFecIni(Input.inFecIni());
				break;
			case 2:
				filtro.setFecFin(Input.inFecFin());
				break;
			case 3:
				filtro.setFecIni(Input.inFecIni());
				filtro.setFecFin(Input.inFecFin());
				break;
			default:
				break; 
			}
			break;
			
		//--------------------------------------------------------------------------------------------------------------
		default:
			break;
			
		}
		return filtro;
	}
	
	private static void printResultadoFiltroReserva(Reserva filtro) {
		RepoReserva rR = new RepoReserva();
		ArrayList<Reserva> lista = rR.getListaFiltrada(filtro);
		
		System.out.print(
				"Fecha Inicio" + "\t| "
				+ "Fecha Fin" + "\t| "
				+ "DNI" + "\t\t| "
				+ "Nombre" + "\t| "
				+ "Apellido/s" + "\t| "
				+ "Hotel" + "\t\t| "
				+ "Numero habitacion" + "\n"
						+ "--------------------------------------------------------------------------------------------------------------------\n");
		int i = 0;
		while (i < lista.size()) {
			System.out.print(
					lista.get(i).getFecIni().toString() + "\t| "
					+ lista.get(i).getFecFin().toString() + "\t| "
					+ lista.get(i).getCliente().getDNI() + "\t| "
					+ (
						(lista.get(i).getCliente().getNombre().length() < 6) ? 
							(lista.get(i).getCliente().getNombre() + "\t\t| ") : 
							(lista.get(i).getCliente().getNombre() + "\t| ")
					)
					+ (
							(lista.get(i).getCliente().getApellidos().length() < 6) ? 
								(lista.get(i).getCliente().getApellidos() + "\t\t| ") : 
								(lista.get(i).getCliente().getApellidos() + "\t| ")
						)
					+ lista.get(i).getSala().getHotel().getNombre() + "\t| "
					+ lista.get(i).getSala().getNum() + "\n"
			);
			i++;
			if (i%5 == 0) System.out.print("--------------------------------------------------------------------------------------------------------------------\n");
		}
		System.out.print("\n");
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

	



}
