package menu;

import java.util.Scanner;

public class MenuPrincipal {
    

    public MenuPrincipal() {

    }
	
    public static void print() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

    do {
    	System.out.println("\n~~~ Menú Principal ~~~");
        System.out.println("\n1. Menu Productos");
        System.out.println("2. Menu Reserva");
        System.out.println("3. Menu Carrito");
        
        if () {
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
        	if () {
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
}
