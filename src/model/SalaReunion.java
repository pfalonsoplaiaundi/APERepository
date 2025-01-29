package model;

import java.util.Objects;

public class SalaReunion {
	
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
	public SalaReunion(String servicios) {
		super();
		this.servicios = servicios;
	}
	
	

}
