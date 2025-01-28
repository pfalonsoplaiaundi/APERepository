package menu;

import java.util.Scanner;

import auxi.Input;
import conectores.ConectMySQL;
import conectores.RepoCliente;
import conectores.RepoHotel;
import model.Cliente;

public class Login {

	public static void main(String[] args) {
		ConectMySQL.conectar();		
		System.out.print(
				"~~~ Bienvenido a APE ~~~\n"
				+ "\n"
				+ "¿Dispones de cuenta? ");
		boolean respuesta = Input.inYesNo();
		if (!respuesta) crearCuenta();
		
		System.out.print("\n~~~ Identificate ~~~\n");
		String user = Input.inDNI();
		String pass = Input.inPass();
		
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
