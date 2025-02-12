package model;

/**
 * Objeto que almacena los datos de los espacios comunes
 */
public class EspacioComun extends Sala {
	
	// Atributos
	private String tipo;

	//-----------------------------------------------------------------------
	
	//Constructor/es
	/**
	 * Constructor de espacio comun basico
	 * @param num
	 * @param capacidad
	 * @param tlfno
	 * @param pvp
	 * @param hotel
	 * @param tipo
	 */
	public EspacioComun(int num, int capacidad, String tlfno, double pvp, Hotel hotel, String tipo) {
		super(num, capacidad, tlfno, pvp, hotel);
		this.tipo = tipo;
	}
	
	//-----------------------------------------------------------------------
		
	// Getter y setters
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	//-----------------------------------------------------------------------
	
	// Metodos basicos
	@Override
	public String toString() {
		return super.toString() 
			+ "Tipo: " + tipo + "\n"
			+ "----------------------------";
	}
	
}
