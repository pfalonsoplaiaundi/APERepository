package model;

import java.util.Date;

public class Reserva {
	
	//Atributos
	private int ID;
	private Date fecIni;
	private Date fecFin;
	private Cliente cliente;
    private Sala sala;
	
	//-----------------------------------------------------------------------
    
	//Constructor
    public Reserva(int iD, Date fecIni, Date fecFin, Cliente cliente, Sala sala) {
		super();
		ID = iD;
		this.fecIni = fecIni;
		this.fecFin = fecFin;
		this.cliente = cliente;
		this.sala = sala;
	}
    
	//-----------------------------------------------------------------------
    
	//Getters & setters
    public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Date getFecIni() {
		return fecIni;
	}

	public void setFecIni(Date fecIni) {
		this.fecIni = fecIni;
	}

	public Date getFecFin() {
		return fecFin;
	}

	public void setFecFin(Date fecFin) {
		this.fecFin = fecFin;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}
	
}
