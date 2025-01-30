package model;

import java.util.Objects;

import auxi.Input;

public class Cliente {
	
	// Enumerado de tarifas
	public static enum tarifas{
		estandar,
		dctoTrabajador,
		dcto5,
		dcto10,
		dcto5por,
		dcto10por,
		dctoNewCliente
	}
	
	//-----------------------------------------------------------------------
	
	// Atributos
	private String DNI;
	private String nombre;
	private String apellidos;
	private int telefono;
	private String email;
	private tarifas tarifa;
	private boolean bTrabajador;
	private String pass;
	
	//-----------------------------------------------------------------------
	
	// Constructor/es
	/**
	 * 	
	 * @param DNI
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param email
	 * @param bTrabajador
	 * @param pass
	 */
	public Cliente(String DNI, String nombre, String apellidos, int telefono, String email,
			boolean bTrabajador, String pass) {
		super();
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.bTrabajador = bTrabajador;
		this.pass = pass;
		this.tarifa = selectorTarifa();
	}
	
	//-----------------------------------------------------------------------

	// Getter y setters

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

	//-----------------------------------------------------------------------
	
	// Metodos propios
	
	public static boolean verificacionDNI(String DNI) {
        // Verificar que el DNI tenga 9 caracteres
        if (DNI == null || DNI.length() != 9) {
            return false;
        }

        // Separar los dígitos y la letra
        String numeros = DNI.substring(0, 8);
        char letra = DNI.charAt(8);

        // Verificar que los primeros 8 caracteres sean dígitos
        if (!numeros.matches("\\d{8}")) {
            return false;
        }

        // Calcular la letra correcta
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(numeros);
        char letraCorrecta = letras.charAt(numero % 23);

        // Comparar la letra calculada con la letra proporcionada
        return letra == letraCorrecta;
	}
	

    public static boolean tieneNumeros(String pass) {
        /**
         * Funcion que comprueba que haya numeros en un string
         */
    	
    	// Comprueba que pass no este vacio
    	if (pass == null) {
            return false;
        }
    	
    	// Revisa caracter a caracter si hay un numero, si llega al final y no lo hay devuelve false
        for (char c : pass.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean tieneMayus(String pass) {
    	/**
         * Funcion que comprueba que haya mayusculas en un string
         */
    	
    	// Comprueba que pass no este vacio
    	if (pass == null) {
            return false;
        }
    	
    	// Revisa caracter a caracter si hay una mayuscula, si llega al final y no la hay devuelve false
        for (char c : pass.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean tieneMinus(String pass) {
    	/**
         * Funcion que comprueba que haya minusculas en un string
         */
    	
    	
    	// Comprueba que pass no este vacio
    	if (pass == null) {
            return false;
        }
    	
    	// Revisa caracter a caracter si hay una minuscula, si llega al final y no la hay devuelva false
        for (char c : pass.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificacionPass(String pass) {
    	/**
    	 * Comprueba que se cumplan todas las condiciones de la contraseña
    	 */
        return tieneNumeros(pass) && tieneMayus(pass) && tieneMinus(pass);
    }
		
	public static boolean verificacionExistencia() {
		/*
		 * WIP
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		return false;
	}
		
    private tarifas selectorTarifa() {
    	/**
    	 * Dependiendo de si el es o no trabajador tendra el descuento pertinente.
    	 */
        if (bTrabajador) {
            return tarifas.dctoTrabajador;
        } else {
            return tarifas.dctoNewCliente;
        }
    }

	@Override
	public int hashCode() {
		return Objects.hash(DNI);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(DNI, other.DNI);
	}

	@Override
	public String toString() {
		return "--Cliente--\n"
				+ "DNI: " + DNI + "\n"
				+ "Nombre: " + nombre + " " + apellidos + "\n"
				+ "Telefono: " + telefono + "\n"
				+ "Email: " + email + "\n"
				+ "Tarifa: " + tarifa + "\n"
				+ ((bTrabajador)?"Trabajador":"Cliente") + "\n"
				+ "Contraseña: " + pass + "\n"
				+ "--------------------";
	}
    
	public static void main(String[] args) {
		Cliente c = new Cliente("464532", "Pepe", "Martin", 943486532, "pepe@pepe.pepe", true, "pepe6");
		System.out.print(c.toString());
	}
    
	
}
