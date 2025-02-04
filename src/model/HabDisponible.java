package model;

import java.util.Objects;

public class HabDisponible {
	
	private Habitacion habitacion;
	private boolean disponible;
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
	public HabDisponible(Habitacion habitacion, boolean disponible) {
		super();
		this.habitacion = habitacion;
		this.disponible = disponible;
	}
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
