package model;

import java.util.Objects;

/**
 * Objeto que almacena la informacion de habitacion y su disponibilidad
 */
public class HabDisponible {
	//-----------------------------------------------------------------------
	
	// Atributos	
	private Habitacion habitacion;
	private boolean disponible;
	
	//-----------------------------------------------------------------------
	
	// Getter y setters
	public boolean isDisponible() {
		return disponible;
	}
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
	public Habitacion getHabitacion() {
		return habitacion;
	}
	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
	//-----------------------------------------------------------------------
	
	// Constructor
	public HabDisponible(Habitacion habitacion, boolean disponible) {
		super();
		this.habitacion = habitacion;
		this.disponible = disponible;
	}
	
	//-----------------------------------------------------------------------
	
	// Metodos basicos
	@Override
	public String toString() {
		return "HabDisponible [habitacion=" + habitacion + ", disponible=" + disponible + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(habitacion);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HabDisponible other = (HabDisponible) obj;
		return Objects.equals(habitacion, other.habitacion);
	}
	
	
}
