package model;

import java.util.ArrayList;
import java.util.Objects;

import menu.Login;

/**
 * Objeto que almacena los datos de cliente
 */
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
	private String telefono;
	private String email;
	private tarifas tarifa;
	private boolean bTrabajador;
	private String pass;
	
	//-----------------------------------------------------------------------
	
	// Constructor/es
	/**
	 * Constructor de cliente en el que la tarifa se pone automaticamente
	 * @param DNI
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param email
	 * @param bTrabajador
	 * @param pass
	 */
	public Cliente(String DNI, String nombre, String apellidos, String telefono, String email,
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
	
	/**
	 * Constructor de cliente en el que la tarifa se da en String
	 * @param DNI
	 * @param nombre
	 * @param apellidos
	 * @param telefono
	 * @param email
	 * @param bTrabajador
	 * @param tarifa
	 * @param pass
	 */
	public Cliente(String DNI, String nombre, String apellidos, String telefono, String email,
			boolean bTrabajador, String tarifa, String pass) {
		super();
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.bTrabajador = bTrabajador;
		this.pass = pass;
		this.tarifa = selectorTarifa(tarifa);
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

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
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
	
	//Metodos basicos
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
		return "\n------ "
				+ (isbTrabajador() ? "Trabajador" : "Cliente")
				+ " ------\n"
				+ "DNI: \t\t" + DNI + "\n"
				+ "Nombre: \t" + nombre + " " + apellidos + "\n"
				+ "Telefono: \t" + telefono + "\n"
				+ "Email: \t\t" + email + "\n"
				+ "Tarifa: \t" + tarifa + "\n"
				+ "---------------------";
	}
	
	//-----------------------------------------------------------------------
		
	// Metodos propios
	/**
	 * Verifica si un DNI es correcto o no
	 * @param DNI
	 * @return
	 */
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
	
    /**
     * Funcion que comprueba que haya numeros en un string
     */
    public static boolean tieneNumeros(String pass) {
    	
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
	
    /**
     * Funcion que comprueba que haya mayusculas en un string
     */
    public static boolean tieneMayus(String pass) {

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

	/**
     * Funcion que comprueba que haya minusculas en un string
     */
    public static boolean tieneMinus(String pass) {
    	
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
    
    /**
     * Comprueba que la contraseña tenga al menos 8 caracteres
     * @param pass
     * @return
     */
    public static boolean tiene8caracteres(String pass) {
    	
    	// Comprueba que pass no este vacio
    	if (pass == null) {
            return false;
        }
    	
    	// Revisa caracter a caracter si hay una minuscula, si llega al final y no la hay devuelva false
    	return pass.length()>=8;
    }

	/**
	 * Comprueba que se cumplan todas las condiciones de la contraseña
	 */
    public static boolean verificacionPass(String pass) {

        return tieneNumeros(pass) && tieneMayus(pass) && tieneMinus(pass) && tiene8caracteres(pass) ;
    }

    /**
	 * Al construir un nuevo cliente le asigna su tarifa
	 * segun si es trabajador o nuevo cliente.
	 */
    private tarifas selectorTarifa() {
    	
        if (bTrabajador) {
            return tarifas.dctoTrabajador;
        } else {
            return tarifas.dctoNewCliente;
        }
    }
    
    /**
     * Transforma una tarifa en string en una tarifa en enum.
     * @param tarifa
     * @return
     */
	private tarifas selectorTarifa(String tarifa) {
		ArrayList<String> t = new ArrayList<>();
		t.add("estandar");
		t.add("dctoTrabajador");
		t.add("dcto5");
		t.add("dcto10");
		t.add("dcto5por");
		t.add("dcto10por");
		t.add("dctoNewCliente");
		
		// Comparto lo que me pasan con el array de arriba y lo transformo en el enumerado
		switch (t.indexOf(tarifa)) {
		case 0:
			return tarifas.estandar;
		case 1:
			return tarifas.dctoTrabajador;
		case 2:
			return tarifas.dcto5;
		case 3:
			return tarifas.dcto10;
		case 4:
			return tarifas.dcto5por;
		case 5:
			return tarifas.dcto10por;
		case 6:
			return tarifas.dctoNewCliente;
		default:
			return tarifas.estandar;
		}
	}

	/**
	 * Transforma una tarifa en un descuento sobre el pvp
	 * @param pvp
	 * @return
	 */
	public double aplicarDcto(double pvp) {
		ArrayList<String> t = new ArrayList<>();
		t.add("estandar");
		t.add("dctoTrabajador");
		t.add("dcto5");
		t.add("dcto10");
		t.add("dcto5por");
		t.add("dcto10por");
		t.add("dctoNewCliente");
		
		// Comparto lo que me pasan con el array de arriba y lo transformo en el enumerado
		switch (t.indexOf(Login.user.getTarifa().toString())) {
		case 0:
			return pvp;
		case 1:
			return pvp*0.8;
		case 2:
			return pvp-5;
		case 3:
			return pvp-10;
		case 4:
			return pvp*0.95;
		case 5:
			return pvp*0.9;
		case 6:
			return pvp*0.85;
		default:
			return pvp;
		}
	}
}
