package model;

import java.util.Objects;

public class SalaReunion {
	
	private String servicios;

	public String getServicios() {
		return servicios;
	}

	public void setServicios(String servicios) {
		this.servicios = servicios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(servicios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SalaReunion other = (SalaReunion) obj;
		return Objects.equals(servicios, other.servicios);
	}

	public SalaReunion(String servicios) {
		super();
		this.servicios = servicios;
	}
	
	

}
