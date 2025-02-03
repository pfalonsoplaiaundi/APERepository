package model;

import java.util.Objects;

public class EspacioComun extends Sala {
	
	private String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public EspacioComun(int num, int capacidad, int tlfno, double pvp, Hotel hotel, String tipo) {
		super(num, capacidad, tlfno, pvp, hotel);
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return super.toString() 
			+ "Tipo: " + tipo + "\n"
			+ "----------------------------";
	}
	
}
