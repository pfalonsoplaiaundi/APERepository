package model;

import java.sql.Date;

/**
 * Objeto que guarda un tipo de habitacion y una fecha
 */
public class HabReserva {

	// Atributos
	private Habitacion.tipoHab tipoHab;
	private Date fecha;
	
	//-----------------------------------------------------------------------
	
	// Getter y setters	
	public Habitacion.tipoHab getTipoHab() {
		return tipoHab;
	}
	public void setTipoHab(Habitacion.tipoHab tipoHab) {
		this.tipoHab = tipoHab;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	//-----------------------------------------------------------------------
	
	// Atributos	
	public HabReserva(model.Habitacion.tipoHab tipoHab, Date fecha) {
		super();
		this.tipoHab = tipoHab;
		this.fecha = fecha;
	}

}
