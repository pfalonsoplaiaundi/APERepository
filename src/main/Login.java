package main;

import java.util.Scanner;

import auxi.Input;

public class Login {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("~~~ Bienvenido a APE ~~~");
		System.out.print("\n¿Quieres crear tu cuenta? (Si/No) ");
		String respuesta = scanner.nextLine().trim().toLowerCase();
		
		if (respuesta.equals("si")) { //SOLICITAR DATOS AL USER
		    
			String DNI = Input.inDNI();

		    String nombre = Input.inNombre();

		    String apellido = Input.inApellido();
		    
		    String gmail = Input.inEmail();

		    String contrasena = Input.inPass();

		    System.out.println("\nCuenta creada exitosamente");

			} else if (respuesta.equals("no")) {
				System.out.println("No se creó ninguna cuenta.");
			} else {
				System.out.println("Respuesta no válida. Por favor, responde con 'Si' o 'No'."); 
			}
				scanner.close();		 

	}
}
