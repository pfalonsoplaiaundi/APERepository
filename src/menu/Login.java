package menu;

import java.util.Scanner;

import auxi.Input;
import conectores.ConectMySQL;
import conectores.RepoCliente;
import conectores.RepoHotel;
import model.Cliente;

public class Login {
	
	public static Cliente user;

	public static void main(String[] args) {
		ConectMySQL.conectar();		
		System.out.print(
				"~~~ Bienvenido a APE ~~~\n"
				+ "\n"
				+ "¿Dispones de cuenta? ");
		boolean respuesta = Input.inYesNo();
		if (!respuesta) crearCuenta();
		
		do {
			System.out.print("\n~~~ Identificate ~~~\n");
			RepoCliente rC = new RepoCliente();
			String DNI = Input.inDNI();
			if (rC.checkCreden(DNI, Input.inPass())) {
				user = rC.get(DNI);
				System.out.print("\\n\\n>>> IDENTIFICACION CORRECTA <<<\\n\\n");
			} else {
				System.out.print("\n\n>>> ERROR EN LA IDENTIFICACION <<<\n\n");
			}
		}while (user == null);
		
		MenuPrincipal.print();
		
		
		
	}
	
	public static void crearCuenta() {
		Cliente c = new Cliente(Input.inDNI(), Input.inNombre(), Input.inApellido(), Input.inTelefono(), Input.inEmail(), false, Input.inPass());
		RepoCliente rC = new RepoCliente();
		rC.insert(c);
			/*
		    System.out.println("\nCuenta creada exitosamente");

			} else if (respuesta.equals("no")) {
				System.out.println("No se creó ninguna cuenta.");
			} else {
				System.out.println("Respuesta no válida. Por favor, responde con 'Si' o 'No'."); 
			}*/
	}
}
