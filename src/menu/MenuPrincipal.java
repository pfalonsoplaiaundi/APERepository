package menu;

import java.util.ArrayList;
import java.util.Scanner;

import auxi.*;
import conectores.*;

public class MenuPrincipal {
   
	public static void print() {
		System.out.print(
				"\n\n~~~ Menu principal ~~~\n"
				+ "------------------------------\n"
				);
		RepoHotel rH = new RepoHotel();
		ArrayList<String> hoteles = rH.getMenuPrincipal();
		for(int i = 0; i < hoteles.size(); i++) {
			System.out.print((i+1) + ". " + hoteles.get(i) + "\n");
		}
		System.out.print("-----------------------------\n"
				+ "Seleccione el hotel que desea: ");
		int opc = Input.inOpc();
		MenuProductos.print(rH.getPKByName(hoteles.get(opc-1)));
		
	}
	
	
	
	
	
	/*
	private boolean esAdmin;
    
    /* Why?! No es un objeto.
    
    private String nombreUsuario;

    public MenuPrincipal(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        //this.esAdmin = 
    }
	
	
    public static void print() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

    do {
    	System.out.println("\n~~~ Menú Principal ~~~");
        System.out.println("\n1. Menu Productos");
        System.out.println("2. Menu Reserva");
        System.out.println("3. Menu Carrito");
        
        if (esAdmin) {
        	System.out.println("4. Menu Admin");
        }
        System.out.println("0. Salir");

        System.out.print("\nSeleccione una opción: ");
        opcion = scanner.nextInt();

        switch (opcion) {
        case 1:
        	new MenuProductos().print();
        	break;
        case 2:
            new MenuReserva().print();
            break;
        case 3:
            new MenuCarrito().print();
            break;
        case 4:
        	if (esAdmin) {
            new MenuAdmin().print();
            } else {
            	System.out.println("Acceso denegado: Solo los administradores pueden acceder a este menú.");
            }
            break;
        case 0:
            System.out.println("Saliendo de la aplicacion...");
            break;
        default:
            System.out.println("Opción no válida, intentalo otra vez.");
        }
    } while (opcion != 0);
    
    scanner.close();
    
    }

    /* No es necesario hacer un main por clase. Una clase puede no tener nada
     * Ademas, el nombre de usuario se lo deberias pedir en el login.
     * 
     * 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce tu nombre de usuario: ");
        String usuario = scanner.nextLine();

        MenuPrincipal menu = new MenuPrincipal(usuario);
        menu.print();
        
        scanner.close();
        
    }
    */
}
