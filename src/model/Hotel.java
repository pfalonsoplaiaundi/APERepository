package model;

import conectores.RepoHotel;

public class Hotel {

	// Atributos
	private int ID;
    private String nombre;
    private String ciudad;
    private String dir; 
    private String tlfno; 
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

	public String getTlfno() {
		return tlfno;
	}

	public void setTlfno(String tlfno) {
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
    public Hotel(int ID, String nombre, String ciudad, String dir, String tlfno, String email) {
        this.ID = ID;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.dir = dir;
        this.tlfno = tlfno;
        this.email = email;
    }
    
	//-----------------------------------------------------------------------
    
    // Metodos propios
    public void printTamano() {
        System.out.print("El hotel " + this.nombre + " tiene habitaciones para " + RepoHotel.getTamano(this.ID)); 
    }

    public boolean verificarDisponibilidad() {
    	if (RepoHotel.getTamano(this.ID) == 0) {
    		return false;
    	} else {
    		return true;
    	}
    }

    public boolean crearSala() {
    	/* WIP
    	
    	int opc = 0;
    	int i = 0;
    	do {
    		Sala.printTipo();
    		opc = Input.inOpc();
    		if(Sala.selectorTipo(opc) == null) {
    			opc = 0;
    			i++;
    		} else {
    			if (Sala.selectorTipo(opc) = tSala) {
    				
    			}
    			return true;
    		}
    	} while (opc == 0 || i == 10);
        
        */
        return false; 
    }
    
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
