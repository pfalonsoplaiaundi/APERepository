package menu;

import conectores.RepoReserva;
import model.Reserva;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuReserva {
    private RepoReserva repoReserva;
    private Scanner scanner;

    // Constructor
    public MenuReserva(RepoReserva repoReserva) {
        this.repoReserva = repoReserva;
        this.scanner = new Scanner(System.in);
    }

    // Método para imprimir el menú
    public void print() {
        System.out.println("===== Menú de Reservas =====");
        System.out.println("1. Crear una nueva reserva");
        System.out.println("2. Confirmar una reserva");
        System.out.println("3. Salir");
    }

    // Método para manejar la selección de opciones
    public void select() {
        boolean exit = false;

        while (!exit) {
            print();
            System.out.print("Seleccione una opción: ");

            int option = validarEntero();

            switch (option) {
                case 1:
                    if (crearReserva()) {
                        System.out.println("Reserva creada exitosamente.");
                    } else {
                        System.out.println("Error al crear la reserva.");
                    }
                    break;
                case 2:
                    if (confirmarReserva()) {
                        System.out.println("Reserva confirmada exitosamente.");
                    } else {
                        System.out.println("Error al confirmar la reserva.");
                    }
                    break;
                case 3:
                    System.out.println("Saliendo del menú...");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Método para crear una nueva reserva
    public boolean crearReserva() {
        try {
            System.out.println("Ingrese los datos de la reserva:");
            System.out.print("ID del cliente: ");
            String idCliente = scanner.next();
            System.out.print("Fecha de inicio (DD-MM-YYYY): ");
            String fechaInicio = scanner.next();
            System.out.print("Fecha de fin (DD-MM-YYYY): ");
            String fechaFin = scanner.next();

            Reserva nuevaReserva = new Reserva(idCliente, fechaInicio, fechaFin);

            if (repoReserva.insert(nuevaReserva)) {
                System.out.println("RepoReserva: Reserva creada exitosamente.");
                return true;
            }
        } catch (Exception e) {
            mostrarError("Error al crear la reserva", e);
        }
        return false;
    }

    // Método para confirmar una reserva existente 
    public boolean confirmarReserva() {
        try {
            System.out.println("Ingrese el ID de la reserva a confirmar:");
            System.out.print("ID de la reserva: ");
            String idReserva = scanner.next();

            Reserva reserva = repoReserva.get(idReserva);
            if (reserva != null) {
                reserva.setConfirmada(true);
                if (repoReserva.update(reserva)) {
                    System.out.println("RepoReserva: Reserva confirmada exitosamente.");
                    return true;
                }
            } else {
                System.out.println("RepoReserva: Reserva no encontrada.");
            }
        } catch (Exception e) {
            mostrarError("Error al confirmar la reserva", e);
        }
        return false;
    }

    // Método auxiliar para validar enteros
    private int validarEntero() {
        while (true) {
            try {
                int num = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer
                return num;
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
                scanner.nextLine(); // Limpiar el buffer
            }
        }
    }

    // Método auxiliar para mostrar errores
    private void mostrarError(String mensaje, Exception e) {
        System.out.println(mensaje + " - Error: " + e.getMessage());
    }

    // Método main para ejecutar el programa
    public static void main(String[] args) {
        RepoReserva repoReserva = new RepoReserva(); 
        MenuReserva menuReserva = new MenuReserva(repoReserva);
        menuReserva.select(); 
    }
}
