package model;

import auxi.Input;

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
	private tipoHab tipo;

	//-----------------------------------------------------------------------
	
	// Getter y setters
	public tipoHab getTipo() {
		return tipo;
	}

	public void setTipo(tipoHab tipo) {
		this.tipo = tipo;
	}

	//-----------------------------------------------------------------------
	
	// Constructor	
	public Habitacion(int num, int capacidad, int tlfno, Hotel hotel, tipoHab tipo) {
		super(num, capacidad, tlfno, hotel);
		this.tipo = selectorTipoHab(capacidad);
	}

	//-----------------------------------------------------------------------
	
	// Metodos propios
	private tipoHab selectorTipoHab(int capacidad) {
		/**
		 * Esta funcion permite elergir el tipo de habitacion que es al construirse una habitacion
		 */
		
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
		return super.toString() 
			+ "Tipo: " + tipo.toString() + "\n"
			+ "----------------------------";
	}
	
	
}

