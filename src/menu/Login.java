package menu;

import auxi.Input;
import conectores.ConectMySQL;
import conectores.RepoCliente;
import model.Cliente;

/**
 * INICIO DEL PROGRAMA, clase que logea al usuario o en su defecto crea un nuevo usuario
 */
public class Login {
	
	public static Cliente user;

	/**
	 * INICIO DEL PROGRAMA
	 * @param args
	 */
	public static void main(String[] args) {

		// Conecta con permisos minimos para poder revisar usuarios, en caso de que falle lo intenta 5 veces y luego termina.
		int i = 0;
		boolean conecta = false;
		
		do {
			i++;
			conecta = (!ConectMySQL.conectar()) && (i <= 5);
		} while(conecta);
		
		if (i > 5) System.exit(0);
		
		// Bienvenida y pregunta si hay cuenta.
		System.out.print(
				"~~~ Bienvenido a APE ~~~\n"
				+ "\n"
				+ "¿Dispones de cuenta? ");
		boolean respuesta = Input.inYesNo();
		
		// Si no hay cuenta, obliga a crear una correctamente.
		if (!respuesta) while(!crearCuenta()) {};
		
		// Hasta este punto user esta vacio, por lo que el resto del programa en caso de saltarselo puede fallar.
		identificarse(); 
		
		// En caso de ser trabajador, le da permisos de adm.
		if (user.isbTrabajador()) ConectMySQL.conectarAdm(); 
		
		// Entra en el programa.
		MenuPrincipal.print();
		
	}
	
	/**
	 * Sirve para crear una cuenta, y devuelve true o false dependiendo de si se ha podido crear e insertar.
	 */
	public static boolean crearCuenta() {
		System.out.print("\n~~~ Creacion de cuenta ~~~\n");
		Cliente c = new Cliente(
			Input.inDNI(), 
			Input.inNombre(), 
			Input.inApellido(), 
			Input.inTelefono(), 
			Input.inEmail(), 
			false, 
			Input.inPass()
		);
		RepoCliente rC = new RepoCliente(); 
		return rC.insert(c);
	}
	
	/**
	 * Esta funcion, muestra el menu de identificacion y comprueba que las credenciales sean correctas.
	 */
	public static void identificarse() {
		do {
			System.out.print("\n~~~ Identificate ~~~\n");
			RepoCliente rC = new RepoCliente();
			String DNI = Input.inDNI();
			if (rC.checkCreden(DNI, Input.inPass())) {
				user = rC.get(DNI);
				System.out.print("\n\n>>> IDENTIFICACION CORRECTA <<<\n\n");
			} else {
				System.out.print("\n\n>>> ERROR EN LA IDENTIFICACION <<<\n\n"
						+ "¿Quieres recuperar su contraseña? ");
				boolean respuesta = Input.inYesNo();
				if (respuesta) {
					System.out.print("\nContacte con nuestro servicio de atencion al cliente por telefono para ello\n");
					main(null);
				} else {
					identificarse();
				}
				
			}
		} while (user == null);
	}
}
