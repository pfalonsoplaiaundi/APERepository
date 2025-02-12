package model;

import java.util.ArrayList;

import auxi.Input;

/**
 * Objeto HABITACION
 */
public class Habitacion extends Sala{
	
	// Enumerado
	public static enum tipoHab {
		individual,
		doble,
		familiar,
		suite,
		apartamento,
		desconocido
	}
	
	//-----------------------------------------------------------------------
	
	// Atributos
	private tipoHab tHab;

	//-----------------------------------------------------------------------
	
	// Getter y setters
	public tipoHab getTipo() {
		return tHab;
	}

	public void setTipo(tipoHab tipo) {
		this.tHab = tipo;
	}

	//-----------------------------------------------------------------------
	
	// Constructor/es
	public Habitacion(Hotel hotel, int num, int capacidad, String tlfno, double pvp, tipoHab tipo) {
		super(num, capacidad, tlfno, pvp, hotel, Sala.tSala.Habitacion);
		this.tHab = tipo;
	}
	
	public Habitacion(Hotel hotel, int num, int capacidad, String tlfno, double pvp, String tipo) {
		super(num, capacidad, tlfno, pvp, hotel, Sala.tSala.Habitacion);
		this.tHab = selectorTipoHab(tipo);
	}

	//-----------------------------------------------------------------------
	
	// Metodos propios
	/**
	 * Automaticamente te pone un tipo de habitacion
	 * @param tipo
	 * @return
	 */
	private tipoHab selectorTipoHab(String tipo) {
		ArrayList<String> t = new ArrayList<>();
		t.add("individual");
		t.add("doble");
		t.add("familiar");
		t.add("suite");
		t.add("apartamento");
		
		// Comparto lo que me pasan con el array de arriba y lo transformo en el enumerado
		switch (t.indexOf(tipo)) {
		case 0:
			return tipoHab.individual;
		case 1:
			return tipoHab.doble;
		case 2:
			return tipoHab.familiar;
		case 3:
			return tipoHab.suite;
		case 4:
			return tipoHab.apartamento;
		default:
			return tipoHab.desconocido;
		}
	}
	
	/**
	 * Esta funcion permite elergir el tipo de habitacion que es al construirse una habitacion
	 */
	private tipoHab selectorTipoHab(int capacidad) {
	
		int opc = 0;
		do {
			
			// Menu de seleccion
			System.out.print(
					"¿Que tipo de habitacion es?\n"
					+ "1. Individual\n"
					+ "2. Doble\n"
					+ "3. Familiar\n"
					+ "4. Suite\n"
					+ "5. Apartamento\n"
					+ "Selecciona el tipo: "
			);
			opc = Input.inOpc();
			
			// Actua segun la opcion, comprobando que el tamaño de la habitacion es correcto
			switch (opc) {
			case 1:
				if (capacidad == 1) {
					return tipoHab.individual;  
				} else {
					System.out.print("Esta habitacion es demasiado pequeña para esta opcion");
					opc = 0;
				}
			case 2:
				if (capacidad == 2) {
					return tipoHab.doble;  
				} else {
					System.out.print("Esta habitacion es demasiado pequeña para esta opcion");
					opc = 0;
				}
			case 3:
				if (capacidad >= 3) {
					return tipoHab.familiar;  
				} else {
					System.out.print("Esta habitacion es demasiado pequeña para esta opcion");
					opc = 0;
				}
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

	@Override
	public String toString() {
		return "Habitacion: " + tHab.toString() + "\n" +
				super.toString();
	}
	
	/**
	 * Convierte tipoHab en string
	 * @param tipoDeHab
	 * @return
	 */
	public static tipoHab tipoHabStringToEnum(String tipoDeHab) {
		if (tipoDeHab.equalsIgnoreCase("individual")) {
			return tipoHab.individual;
		} else if (tipoDeHab.equalsIgnoreCase("doble")) {
			return tipoHab.doble;
		} else if (tipoDeHab.equalsIgnoreCase("familiar")) {
			return tipoHab.familiar;
		} else if (tipoDeHab.equalsIgnoreCase("suite")) {
			return tipoHab.suite;
		} else if (tipoDeHab.equalsIgnoreCase("apartamento")) {
			return tipoHab.apartamento;
		} else {
			return null;
		}
	}
	
	
}

