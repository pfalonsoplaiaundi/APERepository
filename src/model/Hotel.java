package model;

public class Hotel {

	//ATRIBUTOS
	private int ID;
    private String nombre;
    private String ciudad;
    private String dir; 
    private int tlfno; 
    private String email;

    //CONSTRUCTOR
    public Hotel(int ID, String nombre, String ciudad, String dir, int tlfno, String email) {
        this.ID = ID;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.dir = dir;
        this.tlfno = tlfno;
        this.email = email;
    }
    
    public double getTamano() {
        
        return 0.0; 
    }

    public boolean verificarDisponibilidad() {

        return true; 
    }

    public boolean crearSala() {
    	
        return true; 
    }
 
}
