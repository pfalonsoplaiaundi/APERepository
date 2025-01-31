package model;

import java.util.Objects;

import auxi.Input;

public class Sala {

	// Enumeracion	
	public enum tSala {
		Habitacion,
		SalaReuniones,
		EspacioComun
	}
	
	//-----------------------------------------------------------------------
	
	// Atributos
	protected int num;
	protected int capacidad;
	protected int tlfno;
	protected double pvp;
	protected Hotel hotel;
	protected tSala tipo;
	
	//-----------------------------------------------------------------------
	
	// Getters y setters
	public double getPvp() {
		return pvp;
	}
	public void setPvp(double num) {
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
	
	//-----------------------------------------------------------------------
	
	// Constructor/es
	public Sala(int num, int capacidad, int tlfno, double pvp, Hotel hotel) {
		super();
		this.num = num;
		this.capacidad = capacidad;
		this.tlfno = tlfno;
		this.pvp = pvp;
		this.hotel = hotel;
	}
	
	//-----------------------------------------------------------------------
	
	// Metodos propios
	@Override
	public String toString() {
		return "Capacidad: " + capacidad + "\n" +
				"En el " + hotel.getNombre() + "\n" +
				hotel.getCiudad() + " " + hotel.getDir() + "\n" +
				"Email: " + hotel.getEmail() + "\n" +
				"Telefono: " + hotel.getTlfno();
	}
	
	public static void printTipo() {
		// Menu de seleccion
		System.out.print(
				"¿Que tipo de sala es?\n"
				+ "1. Habitacion\n"
				+ "2. Sala de reuniones\n"
				+ "3. Espacio comun\n"
				+ "Selecciona el tipo: "
		);
	}
	
	public static tSala selectorTipo(int opc) {
		return null;
	}
	
}
