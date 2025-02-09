/**
 * Paquete para almacenar funciones auxiliares tales como 
 * inputs de usuario o trasformacion de formatos de datos.
 */
package auxi;

import java.io.Console;
import java.sql.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import model.Cliente;
import model.Habitacion;


/**
 * Clase para recoger los inputs del usuario por consola y gestionar 
 * la validacion de los mismos.
 */
public class Input {

	public static Scanner scn = new Scanner(System.in);
	
	/**
	 * Pregunta por el nombre, no admite nombres con numeros
	 * @return un nombre
	 */
	public static String inNombre() {
		System.out.print("Nombre: ");
        String nom = scn.nextLine();
        if (soloNumeros(nom)) {
        	System.out.print("\n>>> ERROR: introduzca un nombre sin numeros <<<\n");
        	return inNombre();
        } else {
        	return nom;
        }
	}
	
	/**
	 * Pregunta por apellido/os, no admite apellidos con numeros
	 * @return todos los apellidos
	 */
	public static String inApellido() {
		System.out.print("Apellido: ");
        String ape = scn.nextLine();
        if (soloNumeros(ape)) {
        	System.out.print("\n>>> ERROR: introduzca unos apellidos sin numeros <<<\n");
        	return inApellido();
        } else {
        	return ape;
        }
	}
	
	/**
	 * Pregunta por el DNI y comprueba que sea correcto
	 * @return DNI correcto
	 */
	public static String inDNI() {
		String DNI = "";
		do {	
			System.out.print("DNI: ");
			DNI = scn.nextLine();
			if (Cliente.verificacionDNI(DNI)) {
				return DNI;
			} else {
				DNI = "";
				System.out.println("ERROR: DNI no valido");
			}
		} while (DNI.equals(""));
		return DNI;
	}
	
	/**
	 * Pregunta por el email y comprueba que sea un email
	 * @return email
	 */
	public static String inEmail() {
		System.out.print("Correo Electronico: ");
		String email = scn.nextLine();
		String[] emailSplit = email.split("@");
		if (emailSplit.length != 2) {
			System.out.print("\n\n>>> Introduce un correo electronico valido <<<\n\n");
			inEmail();
		} else if (!emailSplit[1].contains(".")) {
			System.out.print("\n\n>>> Introduce un correo electronico valido <<<\n\n");
			inEmail();
		}
		return email;
	}
	
	/**
	 * Pregunta por un telefono, no comprueba que sea valido
	 * @return telefono
	 */
	public static String inTelefono() {
		System.out.print("Telefono: ");
		return scn.nextLine();
	}
	
	/**
	 * Pregunta si es trabajador
	 * @return booleano de si es o no trabajador
	 */
	public static boolean inBTrabajador() {
		System.out.print("¿Es trabajador ");
		return inYesNo();
	}
	
	/**
	 * Pregunta por la tarifa a aplicar al cliente
	 * @return la tarifa en formato enum
	 */
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
	
	/**
	 * Pregunta por la ciudad, sin comprobar que sea real
	 * @return ciudad
	 */
	public static String inCiudad() {
		System.out.print("Ciudad: ");
		return scn.nextLine();
	}
	
	/**
	 * Pregunta por la direccion, sin comprobar que sea real
	 * @return direccion completa
	 */
	public static String inDir() {
		System.out.print("Dirección: ");
		return scn.nextLine();
	}
	
	/**
	 * Pregunta por la capacidad de una sala.
	 * @return capacidad de una sala
	 */
	public static int inCapacidad() {
		System.out.print("Capacidad (en personas): ");
		String aforoT = scn.nextLine();
		int aforo = Integer.parseInt(aforoT);
		scn.next();
		return aforo;
	}
	
	/**
	 * Pregunta por el tipo de habitacion que es.
	 * @return el tipo de habitacion en enum.
	 */
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
	
	/**
	 * Pregunta por el tipo de espacio comun que es. 
	 * No comprueba que sea algo concreto.
	 * @return el tipo o tipos que son.
	 */
	public static String inTipoComun() {
		System.out.print("Tipo de espacio comun: ");
		return scn.nextLine();
	}
	
	/**
	 * Pregunta por los servicios de la sala de reuniones. 
	 * No comprueba que sea algo concreto.
	 * @return los servicios de la sala de reuniones.
	 */
	public static String inServicios() {
		System.out.print("Servicios en la sala de reunion: ");
		return scn.nextLine();
	}
	
	/**
	 * Pregunta por la contraseña y verifica que sea correcta.
	 * @return la contraseña que cumpla las caracteristicas minimas
	 */
	public static String inPass() {
		String pass = "";
		do {
			System.out.print("Contraseña: ");
			Console console = System.console();
			if (console == null) {
				pass = scn.nextLine();
			} else {
				pass = new String(console.readPassword());
				// pass = readPassword();
			}
			if (Cliente.verificacionPass(pass)) {
				return pass;
			} else {
				System.out.print(
						"La contraseña debe contener: " +
						((!Cliente.tieneMayus(pass)) ? "Mayusculas " : "") +
						((!Cliente.tieneMinus(pass)) ? "Minusculas " : "") +
						((!Cliente.tieneNumeros(pass)) ? "Numeros " : "") +
						((!Cliente.tiene8caracteres(pass)) ? "8 caracteres\n" : "\n")
						);
				pass = "";
			}
		} while (pass.equals(""));
		return pass;
    }
	
   
/*	public static String readPassword() {
        StringBuilder password = new StringBuilder();
        try {
            while (true) {
                char ch = (char) System.in.read(); // Leer un solo carácter

                if (ch == '\n' || ch == '\r') { // Si es Enter, termina la entrada
                    break;
                } else if (ch == '\b' || ch == 127) { // Si es Backspace
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b"); // Borra el último '*'
                    }
                } else {
                    password.append(ch);
                    System.out.print("*"); // Muestra '*' en vez del carácter
                }
            }
            System.out.println(); // Salto de línea al terminar
        } catch (IOException e) {
            System.out.println("Error al leer la contraseña.");
        }
        return password.toString();
    } */
	
	/**
	 * Recoge la opcion numerica necesaria para moverse entre menus
	 * @return la opcion en numero
	 */
	public static int inOpc() {
		int opc = 0;
		try {		
			opc = scn.nextInt();
			scn.nextLine();
		} catch (InputMismatchException e) {
			scn.nextLine();
			opc = 0;
			System.out.print("\n>>> ERROR: introduzca un numero por favor <<<\n\n"
					+ "Su nueva eleccion es: ");
			return inOpc();
		}
		return opc;
	}

	/**
	 * Recoge una respuesta entre si y no y lo convierte en un booleano
	 */
	public static boolean inYesNo() {
		System.out.print("(Si/No) : ");
		String r = scn.nextLine();
		if (r.equalsIgnoreCase("Si") || r.equals("1") || r.equalsIgnoreCase("s") || r.equalsIgnoreCase("y")) {
			return true;
		} else if (r.equalsIgnoreCase("no") || r.equals("0") || r.equalsIgnoreCase("n")) {
			return false;
		} else {
			System.out.print("\n>>> ERROR: introduzca una respuesta valida <<<\n\n"
					+ "Su nueva eleccion es ");
			return inYesNo();
		}
	}
	
	/**
	 * Recoge por pantalla una fecha en formato numerico y la transforma en date
	 * @return Fecha de inicio en formato date
	 */
	public static Date inFecIni() {
		String fecIniS = "";
		String fecIniSD;
		String fecIniSM;
		String fecIniSY; 
		do {
			System.out.print("~~~ Fecha inicial ~~~\n"
					+ "Dia: ");
			fecIniSD = scn.nextLine();
			
			System.out.print("Mes (número) : ");
			fecIniSM = scn.nextLine();
			
			System.out.print("Año: ");
			fecIniSY = scn.nextLine();
		
		} while (!StringDate.isStringDate(fecIniSY, fecIniSM, fecIniSD));
		
		fecIniSM = StringDate.monthFormat(fecIniSM);
		fecIniSD = StringDate.dayFormat(fecIniSD, fecIniSM, fecIniSY);
		fecIniS = fecIniSY + "-" + fecIniSM + "-" + fecIniSD;
		
		Date fecIni = Date.valueOf(fecIniS);
		return fecIni;
	}
	
	/**
	 * Recoge pro pantalla una fecha en formato numerico y la transforma en date
	 * @return Fecha de fin en formato date
	 */
	public static Date inFecFin() {
		String fecFinS = "";
		String fecFinSD;
		String fecFinSM;
		String fecFinSY; 
		do {
			System.out.print("~~~ Fecha final ~~~\n"
					+ "Dia: ");
			fecFinSD = scn.nextLine();
			
			System.out.print("Mes (número) : ");
			fecFinSM = scn.nextLine();
			
			System.out.print("Año: ");
			fecFinSY = scn.nextLine();
		
		} while (!StringDate.isStringDate(fecFinSY, fecFinSM, fecFinSD));
		
		fecFinSM = StringDate.monthFormat(fecFinSM);
		fecFinSD = StringDate.dayFormat(fecFinSD, fecFinSM, fecFinSY);
		fecFinS = fecFinSY + "-" + fecFinSM + "-" + fecFinSD;
		
		Date fecFin = Date.valueOf(fecFinS);
		return fecFin;
	}
	
	/**
	 * Recoge un precio, da igual si con punto o coma.
	 * @return devuelve el precio en double
	 */
	public static double inPvp() {
		System.out.print("Precio: ");
		String pvpT = scn.nextLine();
		pvpT = pvpT.replace(",", ".");
		double pvp = Double.parseDouble(pvpT);
		return pvp;
	}

	/**
	 * Recoge el numero de habitacion
	 * @return numero de habitacion
	 */
	public static int inNum() {
		System.out.print("Numero de sala: ");
		int num = scn.nextInt();
		scn.nextLine();
		return num;
	}

	/**
	 * Recoge el codigo de reserva
	 * @return codigo de reserva
	 */ 
	public static int inCod() {
		System.out.print("Codigo de la reserva: ");
		int cod = scn.nextInt();
		scn.nextLine();
		return cod;
	}

	/**
	 * Recoge los datos de una tarjeta de credito.
	 * No comprueba que la tarjeta exista, pero si que lo parezca.
	 * Un array con los datos de tarjeta de credito 
	 * [numero tarjeta, Mes caducidad, Año caducidad, CVC]
	 * @return 
	 */
	public static int[] inTarjeta() {
		System.out.print("\nDATOS DE TARJETA DE CREDITO\n"
				+ "------------------------\n"
				+ "Numero de tarjeta (Sin espacios): ");
		int[] num = new int[4]; 
		num[0] = inNumTarjeta();
		scn.nextLine();
		System.out.print("Mes caducidad: ");  
		num[1] = inMes();
		scn.nextLine();
		System.out.print("Año caducidad: ");
		num[2] = inAnoTarjeta();
		scn.nextLine();
		System.out.print("Codigo de seguridad: ");
		num[3] = inCVC();
		scn.nextLine();
		return num;
	}
	
	/**
	 * Pide el numero de CVC y se asegura que parezca uno
	 * @return CVC
	 */
	private static int inCVC() {
		String cvcT = scn.nextLine();
		if (cvcT.length() == 3 || cvcT.length() == 4) {
			if (soloNumeros(cvcT)) {
				int cvc = Integer.parseInt(cvcT);
				return cvc;
			} else {
				System.out.print("\n>>> ERROR: introduzca solo numeros por favor <<<\n\n"
						+ "CVC: ");
				return inMes();
			}
		} else {
			System.out.print("\n>>> ERROR: dato no valido <<<\n\n"
					+ "CVC: ");
			return inMes();
			
		}
	}
	
	/**
	 * Pide el mes de una tarjeta de credito y se asegura que lo sea.
	 * @return mes de caducidad
	 */
	private static int inMes() {
		String monthT = scn.nextLine();
		if (monthT.length() == 1 || monthT.length() == 2) {
			if (soloNumeros(monthT)) {
				int month = Integer.parseInt(monthT);
				if (month > 0 && month < 13) {
					return month;
				} else {
					System.out.print("\nERROR: introduzca un mes valido <<<\n\n"
							+ "Mes:");
					return inMes();
				}
			} else {
				System.out.print("\n>>> ERROR: introduzca solo numeros por favor <<<\n\n"
						+ "Mes: ");
				return inMes();
			}
		} else {
			System.out.print("\n>>> ERROR: dato no valido <<<\n\n"
					+ "Mes: ");
			return inMes();
			
		}
	}
	
	/**
	 * Pide el año de una tarjeta de credito y se asegura que no este caducada.
	 * @return año de caducidad
	 */
	private static int inAnoTarjeta() {
		String yearT = scn.nextLine();
		if (yearT.length() == 1 || yearT.length() == 2) {
			if (soloNumeros(yearT)) {
				int year = Integer.parseInt(yearT);
				if (year > 24 && year < 36) {
					return year;
				} else {
					System.out.print("\nERROR: introduzca un año valido <<<\n\n"
							+ "Año:");
					return inMes();
				}
			} else {
				System.out.print("\n>>> ERROR: introduzca solo numeros por favor <<<\n\n"
						+ "Año: ");
				return inMes();
			}
		} else {
			System.out.print("\n>>> ERROR: dato no valido <<<\n\n"
					+ "Año: ");
			return inMes();
			
		}
	}
	
	/**
	 * Recoge el numero de la tarjeta y determina si es de 
	 * algun tipo habitual segun la numeracion.
	 * @return numero de tarjeta
	 */
	private static int inNumTarjeta() {
		String numT;
		numT = scn.nextLine();
		if (soloNumeros(numT)) {
			if (numT.length() > 11 && numT.length() < 20) {
				if (numT.length() == 16) {
					System.out.print("\nVisa, Mastercard o Discover\n");
				} else if (numT.length() == 15) {
					System.out.print("\nAmerican Expresss\n");
				} else {
					System.out.print("\nOtras tarjetas\n");
				}
				int num = Integer.parseInt(numT);
				return num;
			} else {
				System.out.print("\n>>> ERROR: Introduzca un numero de tarjeta valido <<<\n\n"
						+ "Numero de tarjeta (Sin espacios): ");
				return inNumTarjeta();
			}
		} else {
			System.out.print("\n>>> ERROR: Introduzca el numero de la tarjeta solo con los numeros, sin espacios <<<\n\n"
					+ "Numero de tarjeta (Sin espacios): ");
			return inNumTarjeta();
		}
	}
	
	/**
	 * Se asegura que un String sea solo de numeros.
	 * @param num
	 * @return Si es o no una cadena de numeros
	 */
	private static boolean soloNumeros(String num) {
		for (char c : num.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
	}
}