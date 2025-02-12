package model;

/**
 * Objeto HOTEL
 */
public class Hotel {

	// Atributos
	private int ID;
    private String nombre;
    private String ciudad;
    private String dir; 
    private int tlfno; 
    private String email;

	//-----------------------------------------------------------------------
    
    // Getters y setters
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public int getTlfno() {
		return tlfno;
	}

	public void setTlfno(int tlfno) {
		this.tlfno = tlfno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//-----------------------------------------------------------------------
	
	// Constructor/es
    public Hotel(int ID, String nombre, String ciudad, String dir, int tlfno, String email) {
        this.ID = ID;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.dir = dir;
        this.tlfno = tlfno;
        this.email = email;
    }
    
	//-----------------------------------------------------------------------
	
	// Metodos basicos
    @Override
	public String toString() {
		return "--Hotel--\n"
				+ "ID: " + ID + "\n"
				+ "Nombre: " + nombre + "\n"
				+ "Ciudad: " + ciudad + "\n"
				+ "Direccion:" + dir + "\n"
				+ "Telefono: " + tlfno + "\n"
				+ "Email=" + email + "\n"
				+ "------------------------------";
    
	}

 
}
