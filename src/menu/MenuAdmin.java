package menu;

import java.sql.Date;
import java.util.ArrayList;

import auxi.Input;
import conectores.*;
import model.Habitacion.tipoHab;
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
				"\n¿Que desea modificar?" +
				"1. Cliente\n" +
				"2. Fecha de inicio\n" +
				"3. Fecha de finalizacion\n" +
				"Selecciones la opcion que desee: "
				);
		selectorModifyReserva(Input.inOpc(), aModificar);		
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
	 * @param inOpc
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
		}
	}

	

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
	 * @param filtro
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

	private static void listaHoteles() {
		if (!filtroHotel()) {
			Hotel filtro = new Hotel(0, "", "", "", "", "");
			printResultadoFiltroHotel(filtro);
		}
	}
	
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
	
	private static void printResultadoFiltroHotel(Hotel filtro) {
		RepoHotel rH = new RepoHotel();
		ArrayList<Hotel> lista = rH.getListaFiltrada(filtro);
		
		System.out.print(
				"Nombre" + "\t\t| "
				+ "Ciudad" + "\t\t| "
				+ "Telefono" + "\t| "
				+ "Email" + "\t\t\t\t| "
				+ "Direccion completa\n"
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
							(lista.get(i).getCiudad().length() < 6) ? 
								(lista.get(i).getCiudad() + "\t\t\t| ") : 
								(lista.get(i).getCiudad() + "\t\t| ")
					)
					+ lista.get(i).getTlfno() + "\t| "
					+
					(
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

	private static void printHabitaciones() {
		System.out.print(
				"~~~ Menu de administración / Habitaciones ~~~\n"
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
	 * Imprime el menu de otros de administracion
	 */
	private static void printOtros() {
		System.out.print(
				"~~~ Menu de administración / Otros ~~~\n"
				+ "\n"
				+ "1. Salas de reunion\n"
				+ "2. Espacios comunes\n"
				+ "0. Volver atras\n");
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

	/**
	 * 
	 */
	private static void printComunes() {
		// TODO Auto-generated method stub
		
	}

	private static void printReuniones() {
		// TODO Auto-generated method stub
		
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

	




}
