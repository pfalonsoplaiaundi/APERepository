package model;

import java.sql.Date;

public class HabReserva {
	
	private Habitacion.tipoHab tipoHab;
	private Date fecha;
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
	public HabReserva(model.Habitacion.tipoHab tipoHab, Date fecha) {
		super();
		this.tipoHab = tipoHab;
		this.fecha = fecha;
	}

}
