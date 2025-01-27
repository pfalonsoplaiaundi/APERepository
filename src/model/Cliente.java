package model;

public class Cliente {
	
	public static enum tarifas{
		estandar,
		dctoTrabajador,
		dcto5,
		dcto10,
		dcto5por,
		dcto10por,
		dctoNewCliente
	}
	
	private String DNI;
	private String nombre;
	private String apellidos;
	private int telefono;
	private String email;
	public Cliente(String dNI, String nombre, String apellidos, int telefono, String email,
			boolean bTrabajador, String pass) {
		super();
		DNI = dNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.tarifa = tarifas.estandar;
		this.bTrabajador = bTrabajador;
		this.pass = pass;
	}

	private tarifas tarifa;
	private boolean bTrabajador;
	private String pass;
	
	public String getDNI() {
		return DNI;
	}

	public void setDNI(String dNI) {
		DNI = dNI;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public tarifas getTarifa() {
		return tarifa;
	}

	public void setTarifa(tarifas tarifa) {
		this.tarifa = tarifa;
	}

	public boolean isbTrabajador() {
		return bTrabajador;
	}

	public void setbTrabajador(boolean bTrabajador) {
		this.bTrabajador = bTrabajador;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public static boolean verificacionDNI(String DNI) {
		return false;
	}

}
