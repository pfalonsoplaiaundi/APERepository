package menu;

import java.util.ArrayList;
import auxi.*;
import conectores.*;
import model.Hotel;

public class MenuPrincipal {
   
	public static Hotel hotel;
	
	public static void print() {
		
		System.out.print(
				"\n\n~~~ Menu principal ~~~\n" +
				((Login.user.isbTrabajador()) ? "-1. Para entrar en el menu de admin\n" : "") +
				"0. Para finalizar la compra o salir\n" +
				"------------------------------\n"
				);
		
		RepoHotel rH = new RepoHotel();
		ArrayList<String> hoteles = rH.getMenuPrincipal();
		int i = 0;
		while(i < hoteles.size()) {
			System.out.print((i+1) + ". " + hoteles.get(i) + "\n");
			i++;
		}
		
		System.out.print(
				"-----------------------------\n"
				+ "Cualquier otro numero para volver atras\n" +
				"Seleccione el hotel que desea: "
				);
		int opc = Input.inOpc();
		
		if (opc == 0) {
			System.out.print("¿Quieres deslogearte? ");
			if (Input.inYesNo()) {
				MenuCarrito.print();
			} else {
				print();
			}
		} else if (opc == -1 && Login.user.isbTrabajador()) {
			MenuAdmin.print();
		} else if (opc <= i && opc > 0) {
			int idhotel = rH.getPKByName(hoteles.get(opc-1));
			hotel = rH.get(idhotel);			
			MenuProductos.print(hotel.getID());
		} else {
			Login.main(null);
		}
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
