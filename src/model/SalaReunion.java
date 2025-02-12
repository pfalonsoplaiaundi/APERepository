package model;

/**
 * Objeto sala de reuniones
 */
public class SalaReunion extends Sala {
	
	// Atributos
	private String servicios;

	//-----------------------------------------------------------------------
	
	// Getters y setters
	public String getServicios() {
		return servicios;
	}

	public void setServicios(String servicios) {
		this.servicios = servicios;
	}

	//-----------------------------------------------------------------------
	
	// Constructor
	public SalaReunion(int num, int capacidad, String tlfno, double pvp, Hotel hotel, String servicios) {
		super(num, capacidad, tlfno, pvp, hotel, Sala.tSala.SalaReuniones);
		this.servicios = servicios;
	}
	

}
