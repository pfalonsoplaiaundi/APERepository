package auxi;

import java.util.Scanner;

import model.Cliente;
import model.Habitacion;
import model.Habitacion.tipoHab;

public class Input {

	public static Scanner scn = new Scanner(System.in);
	
	public static String inNombre() {
		System.out.print("Nombre: ");
		return scn.nextLine();
	}
	
	public static String inApellido() {
		System.out.print("Apellido: ");
		return scn.nextLine();
	}
	
	public static String inDNI() {
		System.out.print("DNI: ");
		String DNI = scn.nextLine();
		if(Cliente.verificacionDNI(DNI)) {
			return DNI;
		} else {
			return null;
		}
	}
	
	public static String inEmail() {
		System.out.print("Correo Electronico: ");
		return scn.nextLine();
	}
	
	public static int inTelefono() {
		System.out.print("Telefono: ");
		String tlfnoT = scn.nextLine();
		int tlfno = Integer.parseInt(tlfnoT);
		scn.next();
		return tlfno;
	}
	
	public static boolean inBTrabajador() {
		System.out.print("¿Es trabajador (Y/N)?: ");
		if ( scn.nextLine().equalsIgnoreCase("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Cliente.tarifas inTarifa() {
		System.out.println("|Tarifas disponibles |");
		System.out.println("1. estandar");
		System.out.println("2. descuento trabajador");
		System.out.println("3. descuento 5 euros");
		System.out.println("4. descuento 10 euros");
		System.out.println("5. descuento 5%");
		System.out.println("6. descuento 10%");
		System.out.println("7. descuento por nuevo cliente");
		System.out.print("Elige la tarifa: ");
		int opc = scn.nextInt();
		scn.nextLine();
		
		switch (opc) {
		case 1:
			return Cliente.tarifas.estandar;
		case 2:
			return Cliente.tarifas.dctoTrabajador;
		case 3:
			return Cliente.tarifas.dcto5;
		case 4:
			return Cliente.tarifas.dcto10;
		case 5:
			return Cliente.tarifas.dcto5por;
		case 6:
			return Cliente.tarifas.dcto10por;
		case 7:
			return Cliente.tarifas.dctoNewCliente;
		default:
			return Cliente.tarifas.estandar;
		}
		
	}
	
	public static String inCiudad() {
		System.out.print("Ciudad: ");
		return scn.nextLine();
	}
	
	public static String inDir() {
		System.out.print("Dirección: ");
		return scn.nextLine();
	}
	
	public static int inCapacidad() {
		System.out.print("Capacidad (en personas): ");
		String aforoT = scn.nextLine();
		int aforo = Integer.parseInt(aforoT);
		scn.next();
		return aforo;
	}
	
	public static Habitacion.tipoHab inTipoHab() {
		System.out.println("Tipos de habitacion disponibles ");
		System.out.println("1. individual");
		System.out.println("2. doble");
		System.out.println("3. familiar");
		System.out.println("4. suite");
		System.out.println("5. apartamento");
		System.out.print("Elige el tipo de habitacion: ");
		int opc = scn.nextInt();
		scn.nextLine();
		
		switch (opc) {
		case 1:
			return Habitacion.tipoHab.individual;
		case 2:
			return Habitacion.tipoHab.doble;
		case 3:
			return Habitacion.tipoHab.familiar;
		case 4:
			return Habitacion.tipoHab.suite;
		case 5:
			return Habitacion.tipoHab.apartamento;
		default:
			return Habitacion.tipoHab.desconocido;
		}
	}
	
	public static String inTipoComun() {
		System.out.print("Tipo de espacio comun: ");
		return scn.nextLine();
	}
	
	public static String inServicios() {
		System.out.print("Servicios en la sala de reunion: ");
		return scn.nextLine();
	}
	
	public static String inPass() {
		System.out.print("Contraseña: ");
		return scn.nextLine();
	}
	
	public static int inOpc() {
		int opc = scn.nextInt();
		scn.nextLine();
		return opc;
	}
}
