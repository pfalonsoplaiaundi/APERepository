package menu;

import java.sql.Date;
import java.util.ArrayList;

import auxi.Input;
import conectores.*;
import model.Habitacion.tipoHab;
import model.*;

/**
 * Menu de administracion, solo disponible para trabajadores
 */
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
			break;
		case 2:
			printClientes();
			break;
		case 3:
			printHoteles();
			break;
		case 4:
			printHabitaciones();
			break;
		case 5:
			printOtros();
			break;
		case 0:
			MenuPrincipal.print();
			break;
		default:
			print();
			break;
		}
	}
	
	// --------------------------------------------------------------------------------------------------------------------------------
	
	// Menu administracion / reservas
	
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
	 * Logica del menu de administracion / reservas
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
			deleteReserva();
			break;
		case 0:
			print();
		default:
			printReservas();
		}
		printReservas();
	}
	
	/**
	 * Funcion para eliminar una reserva desde administracion
	 */
	private static void deleteReserva() {
		System.out.print( 
				"¿Que reserva deseas eliminar?\n"
				+ "1. Buscar por CODIGO\n"
				+ "2. Buscar por CLIENTE\n"
				+ "3. Buscar por HOTEL\n"
				+ "4. Buscar por FECHAS\n"
				+ "Seleccione la opcion que deseas: "
				);
		Reserva aBorrar = selectorModifyReservas(Input.inOpc());
		RepoReserva rR = new RepoReserva();
		System.out.print((rR.delete(aBorrar)) ? "Borrado correctamente" : "Error al eliminar");
		
	}

	/**
	 * Funcion para modificar una reserva desde administracion
	 */
	private static void modifyReserva() {
		System.out.print( 
				"\n¿Que reserva deseas modificar?\n"
				+ "1. Buscar por CODIGO\n"
				+ "2. Buscar por CLIENTE\n"
				+ "3. Buscar por HOTEL\n"
				+ "4. Buscar por FECHAS\n"
				+ "Seleccione la opcion que deseas: "
				);
		Reserva aModificar = selectorModifyReservas(Input.inOpc());
		if (aModificar == null) {
			System.out.print("\n>>> ERROR: Reserva no valida <<<\n");
			modifyReserva();
		} else {
		System.out.print(
				"\n~~~ Datos actuales ~~~\n" +
				aModificar.toString() + "\n" +
				"\n¿Que desea modificar?\n" +
				"1. Cliente\n" +
				"2. Fecha de inicio\n" +
				"3. Fecha de finalizacion\n" +
				"Selecciones la opcion que desee: "
				);
		selectorModifyReserva(Input.inOpc(), aModificar);		
		System.out.print("Reserva modificada");
		}
	}

	/**
	 * Logica de modificacion de reservas
	 * @param opc
	 */
	private static void selectorModifyReserva(int opc, Reserva aModificar) {
		RepoReserva rR = new RepoReserva();
		switch (opc) {
		
		// Cliente
		case 1:
			RepoCliente rC = new RepoCliente();
			Cliente c = rC.get(Input.inDNI());
			aModificar.setCliente(c);
			rR.update(aModificar);
			break;
		
		// Fecha de inicio
		case 2:
			Date newFecIni = Input.inFecIni();
			if (aModificar.getFecFin().compareTo(newFecIni) < 0) {
				System.out.print("\n\n>>> ERROR: La nueva fecha es posterior a la finalizacion de la reserva <<<\n");
			} else {
				aModificar.setFecIni(newFecIni);
				rR.update(aModificar);
			}
			break;
			
		// Fecha de fin	
		case 3:	
			Date newFecFin = Input.inFecFin();
			if (aModificar.getFecIni().compareTo(newFecFin) > 0) {
				System.out.print("\n\n>>> ERROR: La nueva fecha es anterior al inicio de la reserva <<<\n");
			} else {
				aModificar.setFecFin(newFecFin);
				rR.update(aModificar);
			}
			break;
		
		default:
			modifyReserva();
		}
	}
	

	/**
	 * Logica para elegir como buscar la reserva a Modificar
	 * @param opc
	 */
	private static Reserva selectorModifyReservas(int opc) {
		Reserva aModificar = null;
		RepoReserva rR = new RepoReserva();
		Reserva filtro = null;
		switch (opc) {
		
		// Buscar por codigo
		case 1:
			aModificar = rR.get(Input.inCod());
			break;
			
		// Buscar por cliente
		case 2:
			// Imprime todas las reservas del cliente 
			RepoCliente rC = new RepoCliente();
			filtro = new Reserva(0, null, null, rC.get(Input.inDNI()), null, 0);
			printReservasCliente(filtro);
			
			// Elige la reserva por codigo
			System.out.print("Seleccione ");
			aModificar = rR.get(Input.inCod());
			break;
		
		// Buscar por hotel
		case 3:
			// Imprime todas las reservas del hotel
			RepoHotel rH = new RepoHotel();
			Sala s = new Sala(0, 0, "", 0, rH.get(rH.getPKByName(Input.inNombre())), "");
			filtro = new Reserva(0, null, null, null, s, 0);
			printReservasCliente(filtro);
			
			// Elige la reserva por codigo
			System.out.print("Seleccione ");
			aModificar = rR.get(Input.inCod());
			break;
		
		// Buscar por fecha
		case 4:
			// Elegir como buscar las reservas
			System.out.print("\n"
					+ "1. Por fecha de inicio\n"
					+ "2. Por fecha de finalizacion\n"
					+ "3. Por ambas fechas\n"
					+ "0. Volver atras\n"
					+ "Seleccione la opcion que desee: ");
			int opc2 = Input.inOpc();
			filtro = new Reserva(0, null, null, null, null, 0);
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
			
			// Elige la reserva por codigo
			System.out.print("Seleccione ");
			aModificar = rR.get(Input.inCod());
			break;
		
		// Vuelve atras
		default:
			modifyReserva();
		}
		return aModificar;
	}

	/**
	 * Impresion de una tabla de reservas segun un filtro
	 * @param filtro
	 */
	private static void printReservasCliente(Reserva filtro) {
		RepoReserva rR = new RepoReserva();
		ArrayList<Reserva> lista = rR.getListaFiltrada(filtro);
		if (lista != null && !lista.isEmpty()) {
		
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
				System.out.print(""
						+ lista.get(i).getID() + "\t| "
						+ lista.get(i).getFecIni().toString() + "\t| "
						+ lista.get(i).getFecFin().toString() + "\t| "
						+ lista.get(i).getCliente().getDNI() + "\t| "
						+ 
						(
							(lista.get(i).getCliente().getNombre().length() < 6) ? 
								(lista.get(i).getCliente().getNombre() + "\t\t| ") : 
								(lista.get(i).getCliente().getNombre() + "\t| ")
						)
						
						+ 
						(
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
			printReservas();
		}
	}
	
	/**
	 * Funcion para crear una reserva desde el menu de administracion
	 */
	private static void addReserva() {
		System.out.print("\n>>> Recuerda tener todos los datos (incluido el numero de habitacion) <<<\n"
				+ "¿Tienes los datos? ");
		if (Input.inYesNo()) {
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
		} else {
			printReservas();
		}
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
				System.out.print("Seleccione la opcion que desea: ");
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
	
	/**
	 * Logica del menu de filtro de reservas
	 * @param filtro
	 * @param opc
	 * @return una reserva con los datos que se quieren filtrar
	 */
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
	
	/**
	 * Imprimer la lista despues de filtrarse por los parametros insertados
	 * @param filtro con aquellos datos que quieras filtrar
	 */
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
	
	// --------------------------------------------------------------------------------------------------------------------------------
	
	// Menu administracion / clientes
	
	/**
	 * Imprime el menu de clientes de administracion
	 */
	private static void printClientes() {
		System.out.print(
				"\n~~~ Menu de administración / Clientes ~~~\n"
				+ "1. Listado\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n"
				+ "Seleccione la opcion que desee: ");
		selectorClientes(Input.inOpc());
	}
	
	/**
	 * Logica del menu clientes de administracion
	 * @param opc
	 */
	private static void selectorClientes(int opc) {
		switch (opc) {
		case 1:
			listaClientes();
			break;
		case 2:
			addCliente();
			break;
		case 3:
			modifyCliente();
			break;
		case 4:
			deleteCliente();
			break;
		case 0:
			print();
		default:
			printClientes();
		}
		printClientes();
	}
	
	/**
	 * Elimina un cliente
	 */
	private static void deleteCliente() {
		System.out.print( 
				"¿Que cliente deseas eliminar?\n"
				+ "1. Buscar por DNI\n"
				+ "2. Buscar por NOMBRE\n"
				+ "3. Buscar por TELEFONO\n"
				+ "4. Buscar por EMAIL\n"
				+ "Seleccione la opcion que deseas: "
				);
		Cliente aBorrar = selectorModifyClientes(Input.inOpc());
		RepoCliente rC = new RepoCliente();
		System.out.print((rC.delete(aBorrar)) ? "Borrado correctamente" : "Error al eliminar");
	}

	/**
	 * Modifica un cliente
	 */
	private static void modifyCliente() {
		System.out.print( 
				"\n¿Que cliente deseas modificar?\n"
				+ "1. Buscar por DNI\n"
				+ "2. Buscar por NOMBRE\n"
				+ "3. Buscar por TELEFONO\n"
				+ "4. Buscar por EMAIL\n"
				+ "Seleccione la opcion que deseas: "
				);
		Cliente aModificar = selectorModifyClientes(Input.inOpc());
		if (aModificar == null) {
			System.out.print("\n>>> ERROR: Cliente no valido <<<\n");
			modifyReserva();
		} else {
		System.out.print(
				"\n~~~ Datos actuales ~~~\n" +
				aModificar.toString() + "\n" +
				"\n¿Que desea modificar?\n" +
				"1. Trabajador o cliente\n" +
				"2. Email\n" +
				"3. Telefono\n" +
				"4. Tarifa\n" +
				"5. Nombre\n" +
				"6. Apellidos\n" +
				"7. Contraseña\n" +
				"0. Volver atras\n" +
				"Selecciones la opcion que desee: "
				);
		selectorModifyCliente(Input.inOpc(), aModificar);
		System.out.print("Cliente modificado");
		}
	}

	/**
	 * Logica de que se modifica del cliente
	 * @param opc
	 * @param aModificar
	 */
	private static void selectorModifyCliente(int opc, Cliente aModificar) {
		RepoCliente rC = new RepoCliente();
		switch (opc) {
		
		// Trabajador o cliente
		case 1:
			System.out.print("¿Es trabajador? ");
			aModificar.setbTrabajador(Input.inYesNo());
			rC.update(aModificar);
			break;
		
		// Email
		case 2:
			aModificar.setEmail(Input.inEmail());
			rC.update(aModificar);
			break;
			
		// Telefono	
		case 3:	
			aModificar.setTelefono(Input.inTelefono());
			rC.update(aModificar);
			break;			
			
		// Tarifa
		case 4:
			aModificar.setTarifa(Input.inTarifa());
			rC.update(aModificar);
			break;
			
		// Nombre
		case 5:
			aModificar.setNombre(Input.inNombre());
			rC.update(aModificar);
			break;
			
		// Apellido
		case 6:
			aModificar.setApellidos(Input.inApellido());
			rC.update(aModificar);
			break;
			
		// Contraseña
		case 7:
			aModificar.setPass(Input.inPass());
			rC.update(aModificar);
			break;
			
		default:
			modifyCliente();	
		}
	}

	/**
	 * Logica de que cliente se va a modificar
	 * @param opc
	 * @return
	 */
	private static Cliente selectorModifyClientes(int opc) {
		Cliente aModificar = null;
		RepoCliente rC = new RepoCliente();
		Cliente filtro = null;
		int trabajador = -1;
		switch (opc) {
		
		// Buscar por DNI
		case 1:
			aModificar = rC.get(Input.inDNI());
			break;
			
		// Buscar por NOMBRE
		case 2:
			filtro = new Cliente("", "", "", "", "", false, "", "-1");
			trabajador = Integer.parseInt(filtro.getPass());
			printResultadoFiltroCliente(filtro, trabajador);
			
			// Elige el cliente por DNI
			System.out.print("Seleccione ");
			aModificar = rC.get(Input.inDNI());
			break;
		
		// Buscar por TELEFONO
		case 3:
			filtro = new Cliente("", "", "", Input.inTelefono(), "", false, "", "-1");
			trabajador = Integer.parseInt(filtro.getPass());
			printResultadoFiltroCliente(filtro, trabajador);
			
			// Elige la reserva por DNI
			System.out.print("Seleccione ");
			aModificar = rC.get(Input.inDNI());
			break;
		
		// Buscar por EMAIL
		case 4:
			filtro = new Cliente("", "", "", "", Input.inEmail(), false, "", "-1");
			trabajador = Integer.parseInt(filtro.getPass());
			printResultadoFiltroCliente(filtro, trabajador);
			
			// Elige la reserva por DNI
			System.out.print("Seleccione ");
			aModificar = rC.get(Input.inDNI());
			break;
		
		// Volver atras
		case 0: 
			printClientes();
		// Vuelve atras
		default:
			modifyCliente();
		}
		return aModificar;
	}

	/**
	 * Añade un nuevo cliente estando logeado como admin
	 */
	private static void addCliente() {
		System.out.print("\n>>> Recuerda tener todos los datos <<<\n"
				+ "¿Tienes los datos? ");
		if (Input.inYesNo()) {
			RepoCliente rC = new RepoCliente();
			System.out.print("¿Es trabajador? ");
			boolean bTrabajador = Input.inYesNo();
			Cliente c = new Cliente(
					Input.inDNI(),
					Input.inNombre(),
					Input.inApellido(),
					Input.inTelefono(),
					Input.inEmail(),
					bTrabajador,
					Input.inPass()
					);
			rC.insert(c);
			System.out.println("");
		} else {
			printClientes();
		}
	}
	
	/**
	 * Dependiendo de si filtra o no los clientes los muestra.
	 */
	private static void listaClientes() {
		if (!filtroCliente()) {
			printResultadoCliente();
		}
	}

	/**
	 * Filtro de lista de clientes
	 * @return
	 */
	private static boolean filtroCliente() {
		System.out.print("\n¿Deseas filtrar el resultado? ");
		boolean respuesta = Input.inYesNo();
		if (respuesta) {

			int opc = 0;
			Cliente filtro = new Cliente("", "", "", "", "", false, "", "-1");
			do {
	
				// Print filtro
				printMenuFiltroCliente(filtro);
				System.out.print("Seleccione la opcion que desea: ");
				opc = Input.inOpc();
				
				// Selector
				filtro = selectorMenuFiltroCliente(filtro, opc);
			} while(opc != 0);
			
			int trabajador = Integer.parseInt(filtro.getPass());
			
			// Print resultado
			printResultadoFiltroCliente(filtro, trabajador);
			return true;
		}
		return false;
	}

	/**
	 * Imprime el menu de filtro cliente
	 * @param filtro
	 */
	private static void printMenuFiltroCliente(Cliente filtro) {
		System.out.print(
				"\n>>> Filtro <<<\n"
				
				// 1. Por nombre		
				+ 														 
				((filtro.getNombre().equals("")) ?
						"1. Por nombre\n" :
						filtro.getNombre() + "\n"
				)
				
				// 2. Cliente o trabajador
				+ 
				(
					(filtro.getPass().equals("-1")) ?
						"2. Trabajador o cliente\n" :
						(filtro.getPass().equals("1")) ?
							"2. Es trabajador\n" :
							"2. Es cliente\n"
				)
				
				// 3. Por telefono
				+ 
				((filtro.getTelefono().equals("")) ?
						"3. Por telefono\n" : 
						filtro.getTelefono() + "\n"
				)
				
				// 4. Por email
				+ 
				((filtro.getEmail().equals("")) ?
						"4. Por email\n" : 
						filtro.getEmail() + "\n"
				)
				
				+ "0. Para continuar\n"
		);
	}

	/**
	 * Logica de construccion del filtro de lista cliente
	 * @param filtro
	 * @param opc
	 * @return
	 */
	private static Cliente selectorMenuFiltroCliente(Cliente filtro, int opc) {
		switch (opc) {
		
		// Filtro nombre
		//--------------------------------------------------------------------------------------------------------------
		case 1:
			filtro.setNombre(Input.inNombre());
			break;
			
		// Filtro trabajador
		//--------------------------------------------------------------------------------------------------------------		
		case 2:
			System.out.print("¿Es trabajador? ");
			boolean respuesta = Input.inYesNo();
			if (respuesta) {
				// Como la contraseña en el filtro no sirve de nada la utilizo para guardar si bTrabajador a sido modificado
				filtro.setPass("1");
				filtro.setbTrabajador(true);
			} else {
				filtro.setPass("0");
				filtro.setbTrabajador(false);
			}
			break;
			
		// Filtro telefono	
		//--------------------------------------------------------------------------------------------------------------
		case 3:
			filtro.setTelefono(Input.inTelefono());
			break;
			
		// Filtro email
		//--------------------------------------------------------------------------------------------------------------
		case 4:
			filtro.setEmail(Input.inEmail());
			break;
			
		//--------------------------------------------------------------------------------------------------------------
		default:
			break;
			
		}
		return filtro;
	}
	
	/**
	 * Imprime tabla resultado clientes
	 * @param filtro
	 * @param trabajador
	 */
	private static void printResultadoFiltroCliente(Cliente filtro, int trabajador) {
		RepoCliente rC = new RepoCliente();
		ArrayList<Cliente> lista = rC.getListaFiltrada(filtro, trabajador);
		
		System.out.print(
				"Nombre" + "\t\t| "
				+ "Apellido/s" + "\t\t| "
				+ "DNI" + "\t\t| "
				+ "Telefono" + "\t| "
				+ "Email" + "\t\t\t\t| "
				+ "Trabajador/Cliente" + "\t| "
				+ "Tarifa" + "\n"
						+ "---------------------------------------------------------------------------------------------------------------------------------------------\n");
		int i = 0;
		while (i < lista.size()) {
			System.out.print(
					(
							(lista.get(i).getNombre().length() < 7) ? 
								(lista.get(i).getNombre() + "\t\t| ") : 
								(lista.get(i).getNombre() + "\t| ")
					)
					+
					(
							(lista.get(i).getApellidos().length() < 6) ? 
								(lista.get(i).getApellidos() + "\t\t\t| ") : 
								(lista.get(i).getApellidos() + "\t\t| ")
					)
					+ lista.get(i).getDNI() + "\t| "
					+ lista.get(i).getTelefono() + "\t| "
					+
					(
							(lista.get(i).getEmail().length() < 22) ? 
								(lista.get(i).getEmail() + "\t\t| ") : 
								(lista.get(i).getEmail() + "\t| ")
					)
					+
					(
							(lista.get(i).isbTrabajador()) ?
									"Trabajador\t\t| " :
									"Cliente\t\t| "
					)
					+ lista.get(i).getTarifa().toString()
					+ "\n"
			);
			i++;
			if (i%5 == 0) System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------\n");
		}
		System.out.print("\n");
	}
	
	/**
	 * Imprime tabla resultado clientes sin filtrar
	 */
	private static void printResultadoCliente() {
		RepoCliente rC = new RepoCliente();
		ArrayList<Cliente> lista = rC.getLista();
		
		System.out.print(
				"Nombre" + "\t\t| "
				+ "Apellido/s" + "\t\t| "
				+ "DNI" + "\t\t| "
				+ "Telefono" + "\t| "
				+ "Email" + "\t\t\t\t| "
				+ "Trabajador/Cliente" + "\t| "
				+ "Tarifa" + "\n"
						+ "---------------------------------------------------------------------------------------------------------------------------------------------\n");
		int i = 0;
		while (i < lista.size()) {
			System.out.print(
					(
							(lista.get(i).getNombre().length() < 7) ? 
								(lista.get(i).getNombre() + "\t\t| ") : 
								(lista.get(i).getNombre() + "\t| ")
					)
					+
					(
							(lista.get(i).getApellidos().length() < 6) ? 
								(lista.get(i).getApellidos() + "\t\t\t| ") : 
								(lista.get(i).getApellidos() + "\t\t| ")
					)
					+ lista.get(i).getDNI() + "\t| "
					+ lista.get(i).getTelefono() + "\t| "
					+
					(
							(lista.get(i).getEmail().length() < 22) ? 
								(lista.get(i).getEmail() + "\t\t| ") : 
								(lista.get(i).getEmail() + "\t| ")
					)
					+
					(
							(lista.get(i).isbTrabajador()) ?
									"Trabajador\t\t| " :
									"Cliente\t\t| "
					)
					+ lista.get(i).getTarifa().toString()
					+ "\n"
			);
			i++;
			if (i%5 == 0) System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------\n");
		}
		System.out.print("\n");
		
	}
	
	// --------------------------------------------------------------------------------------------------------------------------------
	
	// Menu administracion / hoteles
	
	/**
	 * Menu de administracion hoteles
	 */
	private static void printHoteles() {
		System.out.print(
				"\n~~~ Menu de administración / Hoteles ~~~\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n"
				+ "Seleccione la opcion que desee: ");
		selectorHoteles(Input.inOpc());
	}

	/**
	 * Logica del menu de administracion de hoteles
	 * @param opc
	 */
	private static void selectorHoteles(int opc) {
		switch (opc) {
		case 1:
			listaHoteles();
			break;
		case 2:
			addHotel();
			break;
		case 3:
			modifyHotel();
			break;
		case 4:
			deleteHotel();
			break;
		case 0:
			print();
		default:
			printHoteles();
		}
		printHoteles();
	}

	/**
	 * Elimina un hotel
	 */
	private static void deleteHotel() {
		System.out.print( 
				"¿Que cliente deseas eliminar?\n"
				+ "1. Buscar por ID\n"
				+ "2. Buscar por NOMBRE\n"
				+ "3. Buscar por TELEFONO\n"
				+ "4. Buscar por EMAIL\n"
				+ "Seleccione la opcion que deseas: "
				);
		Hotel aBorrar = selectorModifyHoteles(Input.inOpc());
		RepoHotel rH = new RepoHotel();
		System.out.print((rH.delete(aBorrar)) ? "Borrado correctamente" : "Error al eliminar");
	}

	/**
	 * Modifica un hotel
	 */
	private static void modifyHotel() {
		System.out.print( 
				"\n¿Que hotel deseas modificar?\n"
				+ "1. Buscar por ID\n"
				+ "2. Buscar por NOMBRE\n"
				+ "3. Buscar por TELEFONO\n"
				+ "4. Buscar por EMAIL\n"
				+ "Seleccione la opcion que deseas: "
				);
		Hotel aModificar = selectorModifyHoteles(Input.inOpc());
		if (aModificar == null) {
			System.out.print("\n>>> ERROR: Hotel no valido <<<\n");
			modifyHotel();
		} else {
			System.out.print(
					"\n~~~ Datos actuales ~~~\n" +
					aModificar.toString() + "\n" +
					"\n¿Que desea modificar?\n" +
					"1. Nombre\n" +
					"2. Ciudad\n" +
					"3. Direccion\n" +
					"4. Telefono\n" +
					"5. Email\n" +
					"0. Volver atras\n" +
					"Selecciones la opcion que desee: "
					);
			selectorModifyHotel(Input.inOpc(), aModificar);
		}
	}

	/**
	 * Logica de que hotel se va a modificar
	 * @param opc
	 * @return
	 */
	private static Hotel selectorModifyHoteles(int opc) {
		Hotel aModificar = null;
		RepoHotel rH = new RepoHotel();
		Hotel filtro = null;
		switch (opc) {
		
		// Buscar por ID
		case 1:
			aModificar = rH.get(Input.inID());
			break;
			
		// Buscar por NOMBRE
		case 2:
			filtro = new Hotel(0, Input.inNombre(), "", "", "", "");
			printResultadoFiltroHotel(filtro);
			
			// Elige el cliente por DNI
			System.out.print("Seleccione ");
			aModificar = rH.get(Input.inID());
			break;
		
		// Buscar por TELEFONO
		case 3:
			filtro = new Hotel(0, "", "", "", Input.inTelefono(), "");
			printResultadoFiltroHotel(filtro);
			
			// Elige el cliente por DNI
			System.out.print("Seleccione ");
			aModificar = rH.get(Input.inID());
			break;
		
		// Buscar por EMAIL
		case 4:
			filtro = new Hotel(0, "", "", "", "", Input.inEmail());
			printResultadoFiltroHotel(filtro);
			
			// Elige el cliente por DNI
			System.out.print("Seleccione ");
			aModificar = rH.get(Input.inID());
			break;
		
		// Volver atras
		case 0: 
			printClientes();
		// Vuelve atras
		default:
			modifyCliente();
		}
		return aModificar;	
	}

	/**
	 * Logica de que se va a modificar en el hotel
	 * @param opc
	 * @param aModificar
	 */
	private static void selectorModifyHotel(int opc, Hotel aModificar) {
		RepoHotel rH = new RepoHotel();
		switch (opc) {
		
		// Nombre
		case 1:
			aModificar.setNombre(Input.inNombre());
			rH.update(aModificar);
			break;
		
		// Ciudad
		case 2:
			aModificar.setCiudad(Input.inCiudad());
			rH.update(aModificar);
			break;
			
		// Direccion	
		case 3:	
			aModificar.setDir(Input.inDir());
			rH.update(aModificar);
			break;			
			
		// Telefono
		case 4:
			aModificar.setTlfno(Input.inTelefono());
			rH.update(aModificar);
			break;
			
		// Email
		case 5:
			aModificar.setEmail(Input.inEmail());
			rH.update(aModificar);
			break;
			
		// Volver atras	
		default:
			modifyCliente();	
		}
	}

	/**
	 * Añade un hotel
	 */
	private static void addHotel() {
		System.out.print("\n>>> Recuerda tener todos los datos <<<\n"
				+ "¿Tienes los datos? ");
		if (Input.inYesNo()) {
			RepoHotel rH = new RepoHotel();
			Hotel ho = new Hotel(
					0,
					Input.inNombre(),
					Input.inCiudad(),
					Input.inDir(),
					Input.inTelefono(),
					Input.inEmail()
					);
			rH.insert(ho);
			System.out.println("");
		} else {
			printHoteles();
		}
	}

	/**
	 * Lista de hoteles
	 */
	private static void listaHoteles() {
		if (!filtroHotel()) {
			Hotel filtro = new Hotel(0, "", "", "", "", "");
			printResultadoFiltroHotel(filtro);
		}
	}
	
	/**
	 * Filtro de lista de hoteles
	 * @return
	 */
	private static boolean filtroHotel() {
		System.out.print("\n¿Deseas filtrar el resultado? ");
		boolean respuesta = Input.inYesNo();
		if (respuesta) {

			int opc = 0;
			Hotel filtro = new Hotel(0, "", "", "", "", "");
			do {
	
				// Print filtro
				printMenuFiltroHotel(filtro);
				System.out.print("Seleccione la opcion que desea: ");
				opc = Input.inOpc();
				
				// Selector
				filtro = selectorMenuFiltroHotel(filtro, opc);
			} while(opc != 0);
		
			// Print resultado
			printResultadoFiltroHotel(filtro);
			return true;
		}
		return false;
	}
	
	/**
	 * Imprime una tabla resultado de un filtro de hotel
	 * @param filtro
	 */
	private static void printResultadoFiltroHotel(Hotel filtro) {
		RepoHotel rH = new RepoHotel();
		ArrayList<Hotel> lista = rH.getListaFiltrada(filtro);
		
		System.out.print(
				"ID" + "\t| "
				+ "Nombre" + "\t\t| "
				+ "Ciudad" + "\t\t| "
				+ "Telefono" + "\t| "
				+ "Email" + "\t\t\t\t| "
				+ "Direccion completa\n"
				+ "---------------------------------------------------------------------------------------------------------------------------------------------\n");
		int i = 0;
		while (i < lista.size()) {
			System.out.print(
					lista.get(i).getID() + "\t| " +
					(
							(lista.get(i).getNombre().length() < 14) ? 
								(lista.get(i).getNombre() + "\t\t| ") : 
								(lista.get(i).getNombre() + "\t| ")
					)
					+
					(
							(lista.get(i).getCiudad().length() < 6) ? 
								(lista.get(i).getCiudad() + "\t\t\t| ") : 
								(lista.get(i).getCiudad() + "\t\t| ")
					)
					+ lista.get(i).getTlfno() + "\t| "
					+
					(
							(lista.get(i).getEmail().length() < 14) ? 
									(lista.get(i).getEmail() + "\t\t\t| ") : 
									(lista.get(i).getEmail().length() < 22) ? 
										(lista.get(i).getEmail() + "\t\t| ") : 
										(lista.get(i).getEmail() + "\t| ")
					)
					+ lista.get(i).getDir()
					+ "\n"
			);
			i++;
			if (i%5 == 0) System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------\n");
		}
		System.out.print("\n");
	}

	/**
	 * Logica del filtro de hotel
	 * @param filtro
	 * @param opc
	 * @return
	 */
	private static Hotel selectorMenuFiltroHotel(Hotel filtro, int opc) {
		switch (opc) {
		
		// Filtro nombre
		//--------------------------------------------------------------------------------------------------------------
		case 1:
			filtro.setNombre(Input.inNombre());
			break;
			
		// Filtro ciudad
		//--------------------------------------------------------------------------------------------------------------		
		case 2:
			filtro.setCiudad(Input.inCiudad());
			break;
			
		// Filtro telefono	
		//--------------------------------------------------------------------------------------------------------------
		case 3:
			filtro.setTlfno(Input.inTelefono());
			break;
			
		// Filtro email
		//--------------------------------------------------------------------------------------------------------------
		case 4:
			filtro.setEmail(Input.inEmail());
			break;
			
		//--------------------------------------------------------------------------------------------------------------
		default:
			break;
			
		}
		return filtro;
	}

	/**
	 * Menu de filtro de hotel
	 * @param filtro
	 */
	private static void printMenuFiltroHotel(Hotel filtro) {
		System.out.print(
				"\n>>> Filtro <<<\n"
				
				// 1. Por nombre		
				+ 														 
				((filtro.getNombre().equals("")) ?
						"1. Por nombre\n" :
						filtro.getNombre() + "\n"
				)
				
				// 2. Por ciudad
				+ 
				(
					(filtro.getCiudad().equals("")) ?
						"2. Por ciudad\n" :
						filtro.getCiudad() + "\n"
				)
				
				// 3. Por telefono
				+ 
				((filtro.getTlfno().equals("")) ?
						"3. Por telefono\n" : 
						filtro.getTlfno() + "\n"
				)
				
				// 4. Por email
				+ 
				((filtro.getEmail().equals("")) ?
						"4. Por email\n" : 
						filtro.getEmail() + "\n"
				)
				
				+ "0. Para continuar\n"
		);
	}
	
	
	
	// --------------------------------------------------------------------------------------------------------------------------------
	
	// Menu administracion / habitaciones
	
	/**
	 * Menu de administracion de habitaciones
	 */
	private static void printHabitaciones() {
		System.out.print(
				"\n~~~ Menu de administración / Habitaciones ~~~\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n"
				+ "Seleccione la opcion que desee: ");
		selectorHabitaciones(Input.inOpc());	
	}
	
	/**
	 * Selector del menu de administracion - apartado habitaciones
	 * @param opc
	 */
	private static void selectorHabitaciones(int opc) {		
		switch (opc) {
		case 1:
			listaHabitaciones();
			break;
		case 2:
			addHabitacion();
			break;
		case 3:
			modifyHabitacion();
			break;
		case 4:
			deleteHabitacion();
			break;
		case 0:
			print();
		default:
			printHabitaciones();
		}
		printHabitaciones();
	}
	
	/**
	 * Elimina una habitacion
	 */
	private static void deleteHabitacion() {
		System.out.print( 
				"¿Que habitacion deseas eliminar?\n"
				+ "1. Buscar por HOTEL\n"
				+ "2. Buscar por NUMERO\n"
				+ "3. Buscar por TIPO\n"
				+ "4. Buscar por PVP\n"
				+ "Seleccione la opcion que deseas: "
				);
		Habitacion aBorrar = selectorModifyHabitaciones(Input.inOpc());
		RepoHabitacion rH = new RepoHabitacion();
		System.out.print((rH.delete(aBorrar)) ? "Borrado correctamente" : "Error al eliminar");
	}

	/**
	 * Modifica una habitacion
	 */
	private static void modifyHabitacion() {
		System.out.print( 
				"\n¿Que habitacion deseas modificar?\n"
				+ "1. Buscar por HOTEL\n"
				+ "2. Buscar por NUMERO\n"
				+ "3. Buscar por TIPO\n"
				+ "4. Buscar por PVP\n"
				+ "Seleccione la opcion que deseas: "
				);
		Habitacion aModificar = selectorModifyHabitaciones(Input.inOpc());
		if (aModificar == null) {
			System.out.print("\n>>> ERROR: Habitacion no valida <<<\n");
			modifyHabitacion();
		} else {
			System.out.print(
					"\n~~~ Datos actuales ~~~\n" +
					aModificar.toString() + "\n" +
					"\n¿Que desea modificar?\n" +
					"1. Hotel\n" +
					"2. Numero\n" +
					"3. Capacidad\n" +
					"4. Telefono\n" +
					"5. PVP\n" +
					"6. Tipo de habitacion\n" +
					"0. Volver atras\n" +
					"Selecciones la opcion que desee: "
					);
			selectorModifyHabitacion(Input.inOpc(), aModificar);
		}
	}

	/**
	 * Logica sobre que se va a modificar de una habitacion
	 * @param opc
	 * @param aModificar
	 */
	private static void selectorModifyHabitacion(int opc, Habitacion aModificar) {
		RepoHabitacion rH = new RepoHabitacion();
		RepoHotel rHo = new RepoHotel();
		switch (opc) {
		
		// Hotel
		case 1:
			aModificar.setHotel(rHo.get(Input.inID()));
			rH.update(aModificar);
			break;
		
		// Numero
		case 2:
			aModificar.setNum(Input.inNum());
			rH.update(aModificar);
			break;
			
		// Capacidad
		case 3:	
			aModificar.setCapacidad(Input.inCapacidad());
			rH.update(aModificar);
			break;			
			
		// Telefono
		case 4:
			aModificar.setTlfno(Input.inTelefono());
			rH.update(aModificar);
			break;
			
		// PVP
		case 5:
			aModificar.setPvp(Input.inPvp());
			rH.update(aModificar);
			break;
			
		// Tipo de habitacion
		case 6:
			aModificar.setTipo(Input.inTipoHab());
			rH.update(aModificar);
			break;
			
		// Volver atras	
		default:
			modifyCliente();	
		}
	}

	/**
	 * Logica sobre que habitacion va a ser modificada
	 * @param opc
	 * @return
	 */
	private static Habitacion selectorModifyHabitaciones(int opc) {
		Habitacion aModificar = null;
		RepoHabitacion rH = new RepoHabitacion();
		Habitacion filtro = null;
		RepoHotel rHo = new RepoHotel();
		switch (opc) {
		
		// Buscar por HOTEL
		case 1:
			int idHotel = Input.inID();
			filtro = new Habitacion(rHo.get(idHotel), 0, 0, "", 0, "");
			printResultadoFiltroHabitacion(filtro, -1);
			
			// Elige el cliente por Numero
			System.out.print("Seleccione ");
			aModificar = rH.get(idHotel, Input.inNum());
			break;
			
		// Buscar por NUMERO
		case 2:
			int num = Input.inNum();
			filtro = new Habitacion(null, num , 0, "", 0, "");
			printResultadoFiltroHabitacion(filtro, -1);
			
			// Elige el cliente por Hotel
			System.out.print("Seleccione ");
			aModificar = rH.get(rHo.get(Input.inID()).getID(), num);
			break;
		
		// Buscar por TIPO
		case 3:
			filtro = new Habitacion(null, 0, 0, "", 0, Input.inTipoHab());
			printResultadoFiltroHabitacion(filtro, -1);
			
			// Elige el cliente por Numero y Hotel
			System.out.print("Seleccione ");
			aModificar = rH.get(Input.inID(), Input.inNum());
			break;
		
		// Buscar por PVP
		case 4:
			filtro = new Habitacion(null, 0, 0, "", 0, "");
			printResultadoFiltroHabitacion(filtro, -1);
			
			// Elige el cliente por Numero y Hotel
			System.out.print("Seleccione ");
			aModificar = rH.get(Input.inID(), Input.inNum());
			break;
		
		// Volver atras
		case 0: 
			printHabitaciones();
			
		// Vuelve atras
		default:
			modifyHabitacion();
		}
		return aModificar;
	}

	/**
	 * Añade una habitacion
	 */
	private static void addHabitacion() {
		System.out.print("\n>>> Recuerda tener todos los datos <<<\n"
				+ "¿Tienes los datos? ");
		if (Input.inYesNo()) {
			RepoHabitacion rH = new RepoHabitacion();
			RepoHotel rHo = new RepoHotel();
			Habitacion h = new Habitacion(
					rHo.get(Input.inID()),
					Input.inNum(),
					Input.inCapacidad(),
					Input.inTelefono(),
					Input.inPvp(),
					Input.inTipoHab()
					);
			rH.insert(h);
			System.out.println("");
		} else {
			printHoteles();
		}
	}

	/**
	 * Lista de habitaciones
	 */
	private static void listaHabitaciones() {
		if (!filtroHabitaciones()) {
			Habitacion filtro = new Habitacion(null, 0, 0, "", 0, "");
			int disponible = -1;
			printResultadoFiltroHabitacion(filtro, disponible);
		}
	}
	
	/**
	 * Filtro de habitaciones
	 * @return
	 */
	private static boolean filtroHabitaciones() {
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
			return true;	
		}
		return false;
		
	}	

	/**
	 * Imprime el menu de filtro, rellenando los valores ya seteados 
	 * y dejando solo las opciones que esten libres.
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
	
	/**
	 * Imprime una tabla con los datos resultado de habitaciones
	 * @param filtro
	 * @param disponible
	 */
	private static void printResultadoFiltroHabitacion(Habitacion filtro, int disponible) {
		RepoHabitacion rH = new RepoHabitacion();
		System.out.print( "Hotel\t\t| Habitacion\t| Tipo\t\t| Telefono\t| PVP\t\t| Ocupada\n"
			+ "-------------------------------------------------------------------------------------------------\n" );
		int i = 0;
		for(HabDisponible h : rH.getListaFiltrada(filtro, disponible)) {
			System.out.print(
					h.getHabitacion().getHotel().getNombre() + "\t| " + 
					h.getHabitacion().getNum() + "\t\t| " +
					
					((h.getHabitacion().getTipo().toString().length() > 6) ?
					(h.getHabitacion().getTipo().toString() + "\t| ") :
					(h.getHabitacion().getTipo().toString() + "\t\t| ")
					)
					
					+
					
					
					h.getHabitacion().getTlfno() + "\t| " +
					
					((h.getHabitacion().getPvp() >= 100) ?
					(h.getHabitacion().getPvp() + "€\t| ") :
					(h.getHabitacion().getPvp() + "€\t\t| ")
					)
					+
					h.isDisponible() + "\n"
				);
			i++;
			if (i%5 == 0) System.out.print("-------------------------------------------------------------------------------------------------\n");
		};
		System.out.println("");
	}

	/**
	 * Apoyo para el uso de enum TipoHab
	 * @return
	 */
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
	
	// --------------------------------------------------------------------------------------------------------------------------------
	
	// Menu administracion / otros

	/**
	 * Imprime el menu de otros de administracion
	 */
	private static void printOtros() {
		System.out.print(
				"\n~~~ Menu de administración / Otros ~~~\n"
				+ "1. Salas de reunion\n"
				+ "2. Espacios comunes\n"
				+ "0. Volver atras\n"
				+ "Seleccione la opcion que desee: ");
		selectorOtros(Input.inOpc());
	}

	/**
	 * Logica del menu de otros de administracion
	 * @param opc
	 */
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

	// --------------------------------------------------------------------------------------------------------------------------------
	
	// Menu administracion / otros / comunes
	
	/**
	 * Imprime el menu de administracion de espacios comunes
	 */
	private static void printComunes() {
		System.out.print(
				"\n~~~ Menu de administración / Otros / Espacios comunes ~~~\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n"
				+ "Seleccione la opcion que desee: ");
		selectorComunes(Input.inOpc());
	}
	
	/**
	 * Logica del menu de administacion de espacios comunes 
	 * @param opc
	 */
	private static void selectorComunes(int opc) {
		switch (opc) {
		case 1:
			listaComunes();
			break;
		case 2:
			addComun();
			break;
		case 3:
			modifyComun();
			break;
		case 4:
			deleteComun();
			break;
		case 0:
			print();
		default:
			printComunes();
		}
		printOtros();
	}
	
	/**
	 * Elimina un espacio comun 
	 */
	private static void deleteComun() {
		System.out.print( 
				"¿Que espacio comun deseas eliminar?\n"
				+ "1. Buscar por HOTEL\n"
				+ "2. Buscar por NUMERO\n"
				+ "Seleccione la opcion que deseas: "
				);
		EspacioComun aBorrar = selectorModifyComunes(Input.inOpc());
		RepoEspacioComun rEc = new RepoEspacioComun();
		System.out.print((rEc.delete(aBorrar)) ? "Borrado correctamente" : "Error al eliminar");
	}

	/**
	 * Modifica un espacio comun
	 */
	private static void modifyComun() {
		System.out.print( 
				"\n¿Que habitacion deseas modificar?\n"
				+ "1. Buscar por HOTEL\n"
				+ "2. Buscar por NUMERO\n"
				+ "3. Buscar por TIPO\n"
				+ "4. Buscar por PVP\n"
				+ "Seleccione la opcion que deseas: "
				);
		EspacioComun aModificar = selectorModifyComunes(Input.inOpc());
		if (aModificar == null) {
			System.out.print("\n>>> ERROR: Cliente no valido <<<\n");
			modifyComun();
		} else {
			System.out.print(
					"\n~~~ Datos actuales ~~~\n" +
					aModificar.toString() + "\n" +
					"\n¿Que desea modificar?\n" +
					"1. Hotel\n" +
					"2. Numero\n" +
					"3. Capacidad\n" +
					"4. Telefono\n" +
					"5. PVP\n" +
					"6. Tipo de habitacion\n" +
					"0. Volver atras\n" +
					"Selecciones la opcion que desee: "
					);
			selectorModifyComun(Input.inOpc(), aModificar);
		}
	}

	/**
	 * Logica sobre que se va a modifica en un espacio comun
	 * @param opc
	 * @param aModificar
	 */
	private static void selectorModifyComun(int opc, EspacioComun aModificar) {
		RepoEspacioComun rEc = new RepoEspacioComun();
		RepoHotel rHo = new RepoHotel();
		switch (opc) {
		
		// Hotel
		case 1:
			aModificar.setHotel(rHo.get(Input.inID()));
			rEc.update(aModificar);
			break;
		
		// Numero
		case 2:
			aModificar.setNum(Input.inNum());
			rEc.update(aModificar);
			break;
			
		// Capacidad
		case 3:	
			aModificar.setCapacidad(Input.inCapacidad());
			rEc.update(aModificar);
			break;			
			
		// Telefono
		case 4:
			aModificar.setTlfno(Input.inTelefono());
			rEc.update(aModificar);
			break;
			
		// PVP
		case 5:
			aModificar.setPvp(Input.inPvp());
			rEc.update(aModificar);
			break;
			
		// Tipo de habitacion
		case 6:
			aModificar.setTipo(Input.inTipoComun());
			rEc.update(aModificar);
			break;
			
		// Volver atras	
		default:
			modifyCliente();	
		}
	}
	
	/**
	 * Logica sobre que espacio comun va a ser modificado
	 * @param opc
	 * @return
	 */
	private static EspacioComun selectorModifyComunes(int opc) {
		EspacioComun aModificar = null;
		RepoEspacioComun rEc = new RepoEspacioComun();
		EspacioComun filtro = null;
		RepoHotel rHo = new RepoHotel();
		switch (opc) {
		
		// Buscar por HOTEL
		case 1:
			int idHotel = Input.inID();
			filtro = new EspacioComun(0, 0, "", 0, rHo.get(idHotel), "");
			printResultadoFiltroComunes(filtro);
			
			// Elige el cliente por Numero
			System.out.print("Seleccione ");
			aModificar = rEc.get(idHotel, Input.inNum());
			break;
			
		// Buscar por NUMERO
		case 2:
			int num = Input.inNum();
			filtro = new EspacioComun(num, 0, "", 0, null, "");
			printResultadoFiltroComunes(filtro);
			
			// Elige el cliente por Hotel
			System.out.print("Seleccione ");
			aModificar = rEc.get(rHo.get(Input.inID()).getID(), num);
			break;
		
		// Volver atras
		case 0: 
			printHabitaciones();
			
		// Vuelve atras
		default:
			modifyHabitacion();
		}
		return aModificar;
	}

	/**
	 * Añade un espacio comun
	 */
	private static void addComun() {
		System.out.print("\n>>> Recuerda tener todos los datos <<<\n"
				+ "¿Tienes los datos? ");
		if (Input.inYesNo()) {
			RepoEspacioComun rEc = new RepoEspacioComun();
			RepoHotel rHo = new RepoHotel();
			EspacioComun e = new EspacioComun(
					Input.inNum(),
					Input.inCapacidad(),
					Input.inTelefono(),
					Input.inPvp(),
					rHo.get(Input.inID()),
					Input.inTipoComun()
					);
			rEc.insert(e);
			System.out.println("");
		} else {
			printHoteles();
		}
	}

	/**
	 * Lista de espacios comunes
	 */
	private static void listaComunes() {
		if (!filtroComunes()) {
			EspacioComun filtro = new EspacioComun(0, 0, "", 0, null, "");
			printResultadoFiltroComunes(filtro);
		}
	}
	
	/**
	 * Filtro de espacios comunes
	 * @return
	 */
	private static boolean filtroComunes() {
		System.out.print("\n¿Deseas filtrar el resultado? ");
		boolean respuesta = Input.inYesNo();
		if (respuesta) {

			int opc = 0;
			EspacioComun filtro = new EspacioComun(0, 0, "", 0, null, "");
			do {
	
				// Print filtro
				printMenuFiltroComunes(filtro);
				System.out.print("Seleccione la opcion que desea: ");
				opc = Input.inOpc();
				
				// Selector
				filtro = selectorMenuFiltroComunes(filtro, opc);
			} while(opc != 0);
		
			// Print resultado
			printResultadoFiltroComunes(filtro);
			return true;
		}
		return false;
	}
	
	/**
	 * Menu de filtro de espacios comunes
	 * @param filtro
	 */
	private static void printMenuFiltroComunes(EspacioComun filtro) {
		System.out.print(
				"\n>>> Filtro <<<\n"
				
				// 1. Por numero		
				+ 														 
				((filtro.getNum() == 0) ?
						"1. Por nombre\n" :
						filtro.getNum() + "\n"
				)
				
				// 2. Por hotel
				+ 
				(
				(filtro.getHotel() == null) ?
					"2. Por hotel\n" : (
					(filtro.getHotel().getID() == 0) ?
						"2. Por hotel\n" :
						filtro.getHotel().getID() + "\n"
				))
				
				+ "0. Para continuar\n"
		);
	}
	
	/**
	 * Imprime una tabla con los espacios comunes filtrados
	 * @param filtro
	 */
	private static void printResultadoFiltroComunes(EspacioComun filtro) {
		RepoEspacioComun rEC = new RepoEspacioComun();
		ArrayList<EspacioComun> lista = rEC.getListaFiltrada(filtro);
		
		System.out.print(
				"Hotel" + "\t\t| "
				+ "Numero" + "\t| "
				+ "Capacidad" + "\t| "
				+ "Precio" + "\t\t| "
				+ "Telefono" + "\t| "
				+ "Tipo\n"
				+ "---------------------------------------------------------------------------------------------------------------------------------------------\n");
		int i = 0;
		while (i < lista.size()) {
			System.out.print(
					(
							(lista.get(i).getHotel().getNombre().length() < 7) ? 
								(lista.get(i).getHotel().getNombre() + "\t\t| ") : 
								(lista.get(i).getHotel().getNombre() + "\t| ")
					)
					+ lista.get(i).getNum() + "\t\t| "
					+ lista.get(i).getCapacidad() + "\t\t| "
					+ lista.get(i).getPvp() + "€\t\t| "
					+ lista.get(i).getTlfno() + "\t| "
					+ lista.get(i).getTipo() + "\n"
			);
			i++;
			if (i%5 == 0) System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------\n");
		}
		System.out.print("\n");
	}
	
	/**
	 * Logica del filtro de espacios comunes
	 * @param filtro
	 * @param opc
	 * @return
	 */
	private static EspacioComun selectorMenuFiltroComunes(EspacioComun filtro, int opc) {
		switch (opc) {
		
		// Filtro numero
		//--------------------------------------------------------------------------------------------------------------
		case 1:
			filtro.setNum(Input.inNum());
			break;
			
		// Filtro hotel
		//--------------------------------------------------------------------------------------------------------------		
		case 2:
			RepoHotel rH = new RepoHotel();
			filtro.setHotel(rH.get(Input.inID()));
			break;
			
		//--------------------------------------------------------------------------------------------------------------
		default:
			break;
			
		}
		return filtro;
	}
	
	// --------------------------------------------------------------------------------------------------------------------------------
	
	// Menu administracion / otros / reuniones

	/**
	 * Imprime el menu de administracion de salas de reuniones
	 */
	private static void printReuniones() {
		System.out.print(
				"\n~~~ Menu de administración / Otros / Sala de reuniones ~~~\n"
				+ "1. Lista\n"
				+ "2. Agregar\n"
				+ "3. Modificar\n"
				+ "4. Eliminar\n"
				+ "0. Volver atras\n"
				+ "Seleccione la opcion que desee: ");
		selectorReuniones(Input.inOpc());
	}

	/**
	 * Logica del menu de administracion de salas de reuniones
	 * @param opc
	 */
	private static void selectorReuniones(int opc) {
		switch (opc) {
		case 1:
			listaReuniones();
			break;
		case 2:
			addReunion();
			break;
		case 3:
			modifyReuniones();
			break;
		case 4:
			deleteReuniones();
			break;
		case 0:
			print();
		default:
			printReuniones();
		}
		printOtros();
	}
	
	/**
	 * Elimina salas de reuniones
	 */
	private static void deleteReuniones() {
		System.out.print( 
				"¿Que sala de reuniones deseas eliminar?\n"
				+ "1. Buscar por HOTEL\n"
				+ "2. Buscar por NUMERO\n"
				+ "Seleccione la opcion que deseas: "
				);
		SalaReunion aBorrar = selectorModifyReuniones(Input.inOpc());
		RepoSalaReunion rSr = new RepoSalaReunion();
		System.out.print((rSr.delete(aBorrar)) ? "Borrado correctamente\n" : "Error al eliminar\n");
	}

	/**
	 * Modifica una sala de reuniones
	 */
	private static void modifyReuniones() {
		System.out.print( 
				"\n¿Que sala de reuniones deseas modificar?\n"
				+ "1. Buscar por HOTEL\n"
				+ "2. Buscar por NUMERO\n"
				+ "Seleccione la opcion que deseas: "
				);
		SalaReunion aModificar = selectorModifyReuniones(Input.inOpc());
		if (aModificar == null) {
			System.out.print("\n>>> ERROR: Sala de reuniones no valido <<<\n");
			modifyReuniones();
		} else {
			System.out.print(
					"\n~~~ Datos actuales ~~~\n" +
					aModificar.toString() + "\n" +
					"\n¿Que desea modificar?\n" +
					"1. Hotel\n" +
					"2. Numero\n" +
					"3. Capacidad\n" +
					"4. Telefono\n" +
					"5. PVP\n" +
					"6. Saervicios\n" +
					"0. Volver atras\n" +
					"Selecciones la opcion que desee: "
					);
			selectorModifyReunion(Input.inOpc(), aModificar);
		}
	}

	/**
	 * Logica sobre que se va a modifica en una sala de reuniones
	 * @param opc
	 * @param aModificar
	 */
	private static void selectorModifyReunion(int opc, SalaReunion aModificar) {
		RepoSalaReunion rSr = new RepoSalaReunion();
		RepoHotel rHo = new RepoHotel();
		switch (opc) {
		
		// Hotel
		case 1:
			aModificar.setHotel(rHo.get(Input.inID()));
			rSr.update(aModificar);
			break;
		
		// Numero
		case 2:
			aModificar.setNum(Input.inNum());
			rSr.update(aModificar);
			break;
			
		// Capacidad
		case 3:	
			aModificar.setCapacidad(Input.inCapacidad());
			rSr.update(aModificar);
			break;			
			
		// Telefono
		case 4:
			aModificar.setTlfno(Input.inTelefono());
			rSr.update(aModificar);
			break;
			
		// PVP
		case 5:
			aModificar.setPvp(Input.inPvp());
			rSr.update(aModificar);
			break;
			
		// Servicios
		case 6:
			aModificar.setServicios(Input.inServicios());
			rSr.update(aModificar);
			break;
			
		// Volver atras	
		default:
			modifyCliente();	
		}
	}
	
	/**
	 * Logica sobre que sala de reunion se va a modificar
	 * @param opc
	 * @return
	 */
	private static SalaReunion selectorModifyReuniones(int opc) {
		SalaReunion aModificar = null;
		RepoSalaReunion rSr = new RepoSalaReunion();
		SalaReunion filtro = null;
		RepoHotel rHo = new RepoHotel();
		switch (opc) {
		
		// Buscar por HOTEL
		case 1:
			int idHotel = Input.inID();
			filtro = new SalaReunion(0, 0, "", 0, rHo.get(idHotel), "");
			printResultadoFiltroReuniones(filtro);
			
			// Elige el cliente por Numero
			System.out.print("Seleccione ");
			aModificar = rSr.get(idHotel, Input.inNum());
			break;
			
		// Buscar por NUMERO
		case 2:
			int num = Input.inNum();
			filtro = new SalaReunion(num, 0, "", 0, null, "");
			printResultadoFiltroReuniones(filtro);
			
			// Elige el cliente por Hotel
			System.out.print("Seleccione ");
			aModificar = rSr.get(rHo.get(Input.inID()).getID(), num);
			break;
		
		// Volver atras
		case 0: 
			printReuniones();
			
		// Vuelve atras
		default:
			modifyReuniones();
		}
		return aModificar;
	}

	/**
	 * Añade una sala de reuniones
	 */
	private static void addReunion() {
		System.out.print("\n>>> Recuerda tener todos los datos <<<\n"
				+ "¿Tienes los datos? ");
		if (Input.inYesNo()) {
			RepoSalaReunion rSr = new RepoSalaReunion();
			RepoHotel rHo = new RepoHotel();
			SalaReunion s = new SalaReunion(
					Input.inNum(),
					Input.inCapacidad(),
					Input.inTelefono(),
					Input.inPvp(),
					rHo.get(Input.inID()),
					Input.inServicios()
					);
			rSr.insert(s);
			System.out.println("");
		} else {
			printHoteles();
		}
	}

	/**
	 * Lista de salas de reuniones
	 */
	private static void listaReuniones() {
		if (!filtroReuniones()) {
			SalaReunion filtro = new SalaReunion(0, 0, "", 0, null, "");
			printResultadoFiltroReuniones(filtro);
		}
	}
	
	/**
	 * Filtro de salas de reunion 
	 * @return
	 */
	private static boolean filtroReuniones() {
		System.out.print("\n¿Deseas filtrar el resultado? ");
		boolean respuesta = Input.inYesNo();
		if (respuesta) {

			int opc = 0;
			SalaReunion filtro = new SalaReunion(0, 0, "", 0, null, "");
			do {
	
				// Print filtro
				printMenuFiltroReuniones(filtro);
				System.out.print("Seleccione la opcion que desea: ");
				opc = Input.inOpc();
				
				// Selector
				filtro = selectorMenuFiltroReuniones(filtro, opc);
			} while(opc != 0);
		
			// Print resultado
			printResultadoFiltroReuniones(filtro);
			return true;
		}
		return false;
	}
		
	/**
	 * Imprime un filtro de salas de reuniones
	 * @param filtro
	 */
	private static void printMenuFiltroReuniones(SalaReunion filtro) {
		System.out.print(
				"\n>>> Filtro <<<\n"
				
				// 1. Por numero		
				+ 														 
				((filtro.getNum() == 0) ?
						"1. Por numero de sala\n" :
						filtro.getNum() + "\n"
				)
				
				// 2. Por hotel
				+ 
				(
				(filtro.getHotel() == null) ?
					"2. Por hotel\n" : (
					(filtro.getHotel().getID() == 0) ?
						"2. Por hotel\n" :
						filtro.getHotel().getID() + "\n"
				))
				
				+ "0. Para continuar\n"
		);
	}
	
	/**
	 * Imprime las salas de reuniones ya filtradas
	 * @param filtro
	 */
	private static void printResultadoFiltroReuniones(SalaReunion filtro) {
		RepoSalaReunion rSr = new RepoSalaReunion();
		ArrayList<SalaReunion> lista = rSr.getListaFiltrada(filtro);
		
		System.out.print(
				"Hotel" + "\t\t| "
				+ "Numero" + "\t| "
				+ "Capacidad" + "\t| "
				+ "Precio" + "\t| "
				+ "Telefono" + "\t| "
				+ "Servicios\n"
				+ "---------------------------------------------------------------------------------------------------------------------------------------------\n");
		int i = 0;
		while (i < lista.size()) {
			System.out.print(
					(
							(lista.get(i).getHotel().getNombre().length() < 7) ? 
								(lista.get(i).getHotel().getNombre() + "\t\t| ") : 
								(lista.get(i).getHotel().getNombre() + "\t| ")
					)
					+ lista.get(i).getNum() + "\t\t| "
					+ lista.get(i).getCapacidad() + "\t\t| "
					+ ((lista.get(i).getPvp() >= 100) ? lista.get(i).getPvp() + "€\t| " : lista.get(i).getPvp() + "€\t\t| ")
					+ lista.get(i).getTlfno() + "\t| "
					+ lista.get(i).getServicios() + "\n"
			);
			i++;
			if (i%5 == 0) System.out.print("-----------------------------------------------------------------------------------------------------------------------------------------------------\n");
		}
		System.out.print("\n");
	}

	/**
	 * Logica del filtro de salas de reunion
	 * @param filtro
	 * @param opc
	 * @return
	 */
	private static SalaReunion selectorMenuFiltroReuniones(SalaReunion filtro, int opc) {
		switch (opc) {
		
		// Filtro numero
		//--------------------------------------------------------------------------------------------------------------
		case 1:
			filtro.setNum(Input.inNum());
			break;
			
		// Filtro hotel
		//--------------------------------------------------------------------------------------------------------------		
		case 2:
			RepoHotel rH = new RepoHotel();
			filtro.setHotel(rH.get(Input.inID()));
			break;
			
		//--------------------------------------------------------------------------------------------------------------
		default:
			break;
			
		}
		return filtro;
	}

}
