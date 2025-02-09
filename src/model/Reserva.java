package model;

import java.sql.Date;

import conectores.RepoHabitacion;

public class Reserva {
	
	//Atributos
	private int ID;
	private Date fecIni;
	private Date fecFin;
	private Cliente cliente;
    private Sala sala;
    private double precioTotal;
    private boolean bPagada;
	
	//-----------------------------------------------------------------------
    
	//Constructor
    public Reserva(int iD, Date fecIni, Date fecFin, Cliente cliente, Sala sala, double precioTotal) {
		super();
		this.ID = iD;
		this.fecIni = fecIni;
		this.fecFin = fecFin;
		this.cliente = cliente;
		this.sala = sala;
		this.precioTotal = precioTotal;
		this.bPagada = false;
	}
    
	//-----------------------------------------------------------------------
    
	//Getters & setters
    public int getID() {
		return ID;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
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

	public boolean isbPagada() {
		return bPagada;
	}

	public void setbPagada(boolean bPagada) {
		this.bPagada = bPagada;
	}

	@Override
	public String toString() {
		RepoHabitacion rH = new RepoHabitacion();
		Habitacion h = rH.get(sala.getHotel().getID(), sala.getNum());
		return "ID: " + ID + "\n" +
				"Desde el " + fecIni + " hasta el " + fecFin + "\n" + 
				"Cliente: " + cliente.getNombre() + " " + cliente.getApellidos() + "\n" + 
				sala.getTSala() + " " + h.getTipo() + "\n" +
				"Precio: " + precioTotal + "€";
	}
	
}
