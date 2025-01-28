package main;

import java.util.Scanner;

public class MenuPrincipal {
    private boolean esAdmin;
    private String nombreUsuario;

    public MenuPrincipal(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        //this.esAdmin = 
    }

    public void print() {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduce tu nombre de usuario: ");
        String usuario = scanner.nextLine();

        MenuPrincipal menu = new MenuPrincipal(usuario);
        menu.print();
        
        scanner.close();
        
    }
}
