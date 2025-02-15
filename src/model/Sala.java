package model;

import java.util.ArrayList;
import auxi.Input;

/**
 * Objeto SALA
 */
public class Sala {

	// Enumeracion	
	public enum tSala {
		Habitacion,
		SalaReuniones,
		EspaciosComunes
	}
	
	//-----------------------------------------------------------------------
	
	// Atributos
	protected int num;
	protected int capacidad;
	protected int tlfno;
	protected double pvp;
	protected Hotel hotel;
	private tSala tipo;
	
	//-----------------------------------------------------------------------
	
	// Getters y setters
	public double getPvp() {
		return pvp;
	}
	public void setPvp(double pvp) {
		this.pvp = pvp;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getCapacidad() {
		return capacidad;
	}
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}
	public int getTlfno() {
		return tlfno;
	}
	public void setTlfno(int tlfno) {
		this.tlfno = tlfno;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public tSala getTSala() {
		return tipo;
	}
	public void setTSala(tSala tipo) {
		this.tipo = tipo;
	}
	
	//-----------------------------------------------------------------------
	
	// Constructor/es
	public Sala(int num, int capacidad, int tlfno, double pvp, Hotel hotel) {
		super();
		this.num = num;
		this.capacidad = capacidad;
		this.tlfno = tlfno;
		this.pvp = pvp;
		this.hotel = hotel;
		printTipo();
	}
	
	public Sala(int num, int capacidad, String tlfno, double pvp, Hotel hotel, tSala tipo) {
		super();
		this.num = num;
		this.capacidad = capacidad;
		this.tlfno = tlfno;
		this.pvp = pvp;
		this.hotel = hotel;
		this.tipo = tipo;
	}
	
	public Sala(int num, int capacidad, String tlfno, double pvp, Hotel hotel, String tipo) {
		super();
		this.num = num;
		this.capacidad = capacidad;
		this.tlfno = tlfno;
		this.pvp = pvp;
		this.hotel = hotel;
		this.tipo = tSalaStringToTSala(tipo);
	}
	
	//-----------------------------------------------------------------------
	
	// Metodos basicos
	@Override
	public String toString() {
		return "Capacidad: " + capacidad + "\n" +
				"En el " + hotel.getNombre() + "\n" +
				hotel.getCiudad() + " " + hotel.getDir() + "\n" +
				"Email: " + hotel.getEmail() + "\n" +
				"Telefono: " + hotel.getTlfno();
	}
	
	//-----------------------------------------------------------------------
	

	// Metodos propios
	/**
	 * Menu de selecion de tipo de sala
	 */
	public void printTipo() {
		// Menu de seleccion
		System.out.print(
				"¿Que tipo de sala es?\n"
				+ "1. Habitacion\n"
				+ "2. Sala de reuniones\n"
				+ "3. Espacio comun\n"
				+ "Selecciona el tipo: "
		);
		selectorTipo(Input.inOpc());
	}
	
	/**
	 * Logica de seleccion de sala
	 * @param opc
	 */
	public void selectorTipo(int opc) {
		switch (opc) {
		case 1:
			this.tipo = tSala.Habitacion;
		case 2:
			this.tipo = tSala.SalaReuniones;
		case 3:
			this.tipo = tSala.EspaciosComunes;
		default:
			this.tipo = null;
		}
	}
	
	/**
	 * Convierte un string en un TSala
	 * @param tipo
	 * @return
	 */
	private tSala tSalaStringToTSala(String tipo) {
		ArrayList<String> t = new ArrayList<>();
		t.add("Habitacion");
		t.add("SalaReuniones");
		t.add("EspaciosComunes");
		
		// Comparo lo que me pasan con el array de arriba y lo transformo en el enumerado
		switch (t.indexOf(tipo)) {
			case 0: return tSala.Habitacion;
			case 1:	return tSala.SalaReuniones;
			case 2:	return tSala.EspaciosComunes;
			default: return null;
		}
		
	}
	
}
